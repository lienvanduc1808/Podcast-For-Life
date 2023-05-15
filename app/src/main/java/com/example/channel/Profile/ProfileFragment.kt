package com.example.channel.Profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.channel.R
import com.example.channel.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ProfileFragment : Fragment() {
    private lateinit var clPersonalInfo: ConstraintLayout
    private lateinit var clMyPodcast: ConstraintLayout
    private lateinit var clNotification: ConstraintLayout
    private lateinit var clLogout: ConstraintLayout

    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    private lateinit var avatar: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvAddress: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        avatar = view.findViewById(R.id.avatar)
        tvUsername = view.findViewById(R.id.tvUsername)
        tvAddress = view.findViewById(R.id.tvAddress)

        clPersonalInfo = view.findViewById(R.id.clPersonalInfo)
        clPersonalInfo.setOnClickListener {
            replaceFragment(InfoFragment())
        }

        clMyPodcast = view.findViewById(R.id.clMyPodcast)
        clMyPodcast.setOnClickListener {
            replaceFragment(MyPodcastFragment())
        }

        clNotification = view.findViewById(R.id.clNotification)
        clNotification.setOnClickListener {
            replaceFragment(NotiFragment())
        }
        clLogout = view.findViewById(R.id.clLogout)
        clLogout.setOnClickListener {
            val intent = Intent(requireContext(),SignInActivity::class.java)
            startActivity(intent)

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)
        storageReference =  FirebaseStorage.getInstance().getReference("User/"+auth.currentUser?.uid)

        readInfo()
//        val clMyPodcast = view.findViewById<ConstraintLayout>(R.id.clMyPodcast)
//        clMyPodcast.setOnClickListener {
////            val fragment = MyPodcastFragment()
////            parentFragmentManager.beginTransaction()
////                .replace(R.id.frame_layout, fragment)
////
////                .addToBackStack(null)
////                .commit()
//            replaceFragment(MyPodcastFragment())
//        }



    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun readInfo(){
        databaseReference.get().addOnSuccessListener {
            if (it.exists()){
                tvUsername.setText(it.child("name").value.toString())
                tvAddress.setText(it.child("address").value.toString())
            }
            else{
                parentFragmentManager.popBackStack()
            }
        }

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri)
                .into(avatar)
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e(
                "FirebaseStorage",
                "Error getting download URL",
                exception
            )
        }
    }
}