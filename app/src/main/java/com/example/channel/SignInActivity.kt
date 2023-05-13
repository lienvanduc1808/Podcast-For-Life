package com.example.channel

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.channel.admin.AdminMainActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this , gso)


        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnEmailSignIn = findViewById<Button>(R.id.btnEmailSignIn)
        val btnGoogleSignIn = findViewById<Button>(R.id.btnGoogleSignIn)
        val tvSwitchToSignUp = findViewById<TextView>(R.id.tvSwitchToSignUp)






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



    private fun signInWithGoogle() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,10001)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("requestCode",requestCode.toString())
        if(requestCode == 10001){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken , null)

            auth.signInWithCredential(credential).addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    val intent : Intent = Intent(this , MainActivity::class.java)
                    intent.putExtra("email" , account.email)
                    intent.putExtra("name" , account.displayName)
                    Log.d("aimabietemail",account.email.toString())
                    Log.d("name",account.displayName.toString())
                    val userprofile = userData(account.displayName.toString(), "Ho Chi Minh city", account.email.toString())
                    databaseReference = FirebaseDatabase.getInstance().getReference("users")
                    databaseReference.child(auth.currentUser?.uid.toString()).setValue(userprofile).addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(
                                this,
                                "Welcome! , ${account.email.toString()}",
                                Toast.LENGTH_SHORT
                            ).show()
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
                            startActivity(intent)

                        }
                        else{
                            Toast.makeText(
                                this,
                                " Sign in fail!, ${account.email.toString()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                }else{
                    Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
                }
            }

        }
    }




    private fun switchToSignUpFragment() {
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)


    }



    private fun signInWithEmailPassword(email: String, password: String) {
        if (email.equals("admin@gmail.com") && password.equals("123456")) {
            val intent = Intent(this, AdminMainActivity::class.java)
            startActivity(intent)
        } else {
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
    }

    private fun switchToHomeFragment() {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

    }


//    override fun onStart() {
//        super.onStart()
//        if(FirebaseAuth.getInstance().currentUser!=null){
//            val intent : Intent = Intent(this , MainActivity::class.java)
//
//            startActivity(intent)
//        }
//    }


}