package com.example.channel

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.channel.databinding.FragmentSignUpBinding
import com.firebase.ui.auth.AuthUI.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.ref.PhantomReference


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var imageUri: Uri

    private lateinit var etEmail: EditText
    private lateinit var etName: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var tvSwitchToSignIn: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        auth = FirebaseAuth.getInstance()

        etEmail = view.findViewById(R.id.etEmail)
        etName = view.findViewById(R.id.etName)
        etAddress = view.findViewById(R.id.etAddress)
        etPassword = view.findViewById(R.id.etPassword)
        btnSignUp = view.findViewById(R.id.btnSignUp)
        tvSwitchToSignIn = view.findViewById(R.id.tvSwitchToSignIn)

        btnSignUp.setOnClickListener {
            signUpWithEmailPassword(etEmail.text.toString(), etPassword.text.toString())
        }

        tvSwitchToSignIn.setOnClickListener {
            switchToSignInFragment()
        }

        return view
    }

    private fun signUpWithEmailPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign up success, update UI with the signed-in user's information
                    val user = auth.currentUser

                    //New update
                    Toast.makeText(
                        requireContext(),
                        "Sign up successful! Welcome, ${user?.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i("a", user?.uid.toString())
                    databaseReference = FirebaseDatabase.getInstance().getReference("users")
                    Log.i("b", databaseReference.toString())
                    if(user?.uid != null){
                        val userprofile = userData(etName.text.toString(), etAddress.text.toString(), etEmail.text.toString())
                        databaseReference.child(user.uid).setValue(userprofile).addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(
                                    requireContext(),
                                    "Sign up successful! , ${user?.email}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                uploadProfile()
                            }
                            else{
                                Toast.makeText(
                                    requireContext(),
                                    "Sign up fail!, ${user?.email}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                }
            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Sign up fail!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun switchToSignInFragment() {
        val fragment = SignInFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName) //here
            .addToBackStack(null)
            .commit()
    }

    private fun uploadProfile(){
        imageUri = Uri.parse("android.resource://com.example.channel/${R.drawable.avatar_test}")
        storageReference =  FirebaseStorage.getInstance().getReference("User/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnCompleteListener{
            Toast.makeText(
                requireContext(),
                "successful",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener{
            Toast.makeText(
                requireContext(),
                "fail",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
