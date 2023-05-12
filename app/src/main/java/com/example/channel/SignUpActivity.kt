package com.example.channel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.channel.admin.AdminMainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class SignUpActivity : AppCompatActivity() {

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

    companion object {
        private const val RC_SIGN_IN = 9001
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        etName = findViewById(R.id.etName)
        etAddress =findViewById(R.id.etAddress)
        etPassword = findViewById(R.id.etPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        tvSwitchToSignIn = findViewById(R.id.tvSwitchToSignIn)


        btnSignUp.setOnClickListener {
            signUpWithEmailPassword(etEmail.text.toString(), etPassword.text.toString())
        }

        tvSwitchToSignIn.setOnClickListener {
            switchToSignInFragment()
        }

    }

    private fun switchToSignInFragment() {
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
    }

    private fun signUpWithEmailPassword(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(Activity()) { task ->
                if (task.isSuccessful) {
                    // Sign up success, update UI with the signed-in user's information
                    val user = auth.currentUser

                    //New update
                    Toast.makeText(
                        this,
                        "Sign up successful! Welcome, ${user?.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                    switchToSignInFragment()
                    Log.i("a", user?.uid.toString())
                    databaseReference = FirebaseDatabase.getInstance().getReference("users")
                    Log.i("b", databaseReference.toString())
                    if(user?.uid != null){
                        val userprofile = userData(etName.text.toString(), etAddress.text.toString(), etEmail.text.toString())
                        databaseReference.child(user.uid).setValue(userprofile).addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(
                                    this,
                                    "Sign up successful! , ${user?.email}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                uploadProfile()
                            }
                            else{
                                Toast.makeText(
                                    this,
                                    "Sign up fail!, ${user?.email}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                }
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Sign up fail!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun uploadProfile() {
        imageUri = Uri.parse("android.resource://com.example.channel/${R.drawable.avatar_test}")
        storageReference =  FirebaseStorage.getInstance().getReference("User/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnCompleteListener{
            Toast.makeText(
                this,
                "successful",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener{
            Toast.makeText(
                this,
                "fail",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    }


