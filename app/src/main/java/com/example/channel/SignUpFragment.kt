package com.example.channel

import android.content.Intent
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
import com.google.firebase.ktx.Firebase



class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        auth = FirebaseAuth.getInstance()

        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnSignUp = view.findViewById<Button>(R.id.btnSignUp)
        val tvSwitchToSignIn = view.findViewById<TextView>(R.id.tvSwitchToSignIn)

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
                    Toast.makeText(
                        requireContext(),
                        "Sign up successful! Welcome, ${user?.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                    switchToSignInFragment()
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(
                        requireContext(),
                        "Sign up failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun switchToSignInFragment() {
        val fragment = SignInFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName) //here
            .addToBackStack(null)
            .commit()
    }
}
