package com.example.channel

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth



class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var mGoogleApiClient: GoogleApiClient


    companion object {
        private const val RC_SIGN_IN = 9001
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        // googleSignInClient = GoogleSignIn.getClient(this, getGoogleSignInOptions())

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnEmailSignIn = findViewById<Button>(R.id.btnEmailSignIn)
        val btnGoogleSignIn = findViewById<Button>(R.id.btnGoogleSignIn)
        val tvSwitchToSignUp = findViewById<TextView>(R.id.tvSwitchToSignUp)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this) { }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()

        }








        btnEmailSignIn.setOnClickListener {
            Log.d("btnEmail","btnEmail")
            signInWithEmailPassword(etEmail.text.toString(), etPassword.text.toString())
        }




        tvSwitchToSignUp.setOnClickListener {
            switchToSignUpFragment()
        }


    }





    private fun switchToSignUpFragment() {
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)


    }

    private fun signInWithGoogle() {

        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.d("result",result.toString())
            Log.d("resultCode",resultCode.toString())
            handleSignInResult(result)




        }
    }
    private fun handleSignInResult(result: com.google.android.gms.auth.api.signin.GoogleSignInResult) {
//        if (result.isSuccess) {
            val acct = result.signInAccount
            val idToken = acct?.idToken
            val name = acct?.displayName
            val email = acct?.email
            val id = acct?.id
        switchToHomeFragment()
        Toast.makeText(
            this,
            "Sign in successful! ",
            Toast.LENGTH_SHORT
        ).show()

            Log.d("dddddd",name.toString() + email.toString() + id.toString())
//        } else {
//            Log.d(TAG, "Sign in failed")
//        }
    }

    private fun signInWithEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(Activity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(
                        this,
                        "Sign in successful! Welcome, ${user?.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                    switchToHomeFragment()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this,
                        "Sign in failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun switchToHomeFragment() {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

    }


}