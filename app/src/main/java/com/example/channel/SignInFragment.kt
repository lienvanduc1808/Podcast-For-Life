package com.example.channel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.channel.NgheNgay.HomeFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging


class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        auth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), getGoogleSignInOptions())

        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnEmailSignIn = view.findViewById<Button>(R.id.btnGoogleSignIns)
        val btnGoogleSignIn = view.findViewById<Button>(R.id.btnGoogleSignIn)
        val tvSwitchToSignUp = view.findViewById<TextView>(R.id.tvSwitchToSignUp)

        btnEmailSignIn.setOnClickListener {
            signInWithEmailPassword(etEmail.text.toString(), etPassword.text.toString())
        }

        btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }

        tvSwitchToSignUp.setOnClickListener {
            switchToSignUpFragment()
        }

        return view
    }

    private fun signInWithEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(
                        requireContext(),
                        "Sign in successful! Welcome, ${user?.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                    FirebaseMessaging.getInstance().subscribeToTopic("all_users")
                    switchToHomeFragment()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        requireContext(),
                        "Sign in failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun switchToSignUpFragment() {
        val fragment = SignUpFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)//here prob
            .addToBackStack(null)
            .commit()
    }
    private fun switchToHomeFragment() {
        val fragment = HomeFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)//here prob
            .addToBackStack(null)
            .commit()
    }

    private fun getGoogleSignInOptions(): GoogleSignInOptions {
        val default_web_client_id = "869364940749-akjh5mrgpj77i25rubpr1dvfs34m7irj.apps.googleusercontent.com"
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(default_web_client_id)
            .requestEmail()
            .build()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(
                        requireContext(),
                        "Sign in successful! Welcome, ${user?.displayName}",
                        Toast.LENGTH_SHORT
                    ).show()
                    FirebaseMessaging.getInstance().subscribeToTopic("all_users")
                    switchToHomeFragment()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        requireContext(),
                        "Sign in with Google failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(
                    requireContext(),
                    "Google sign in failed. ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}



