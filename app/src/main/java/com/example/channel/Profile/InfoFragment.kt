package com.example.channel.Profile

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.channel.R
import com.example.channel.userData
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class InfoFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    private val PICK_IMAGE: Int = 3
    private lateinit var uri: Uri

    private lateinit var ivBack5: ImageView
    private lateinit var tvBack5: TextView

    private lateinit var avatar: ImageView
    private lateinit var etName: EditText
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText

    private lateinit var btnUpdate: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        ivBack5 = view.findViewById(R.id.ivBack5)
        ivBack5.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        tvBack5 = view.findViewById(R.id.tvBack5)
        tvBack5.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        avatar = view.findViewById(R.id.avatar)
        etName = view.findViewById(R.id.etName)
        etAddress = view.findViewById(R.id.etAddress)
        etEmail = view.findViewById(R.id.etEmail)

        btnUpdate = view.findViewById(R.id.btnUpdate)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        //get currentUser
        auth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)
        storageReference =  FirebaseStorage.getInstance().getReference("User/"+auth.currentUser?.uid)

        readInfo()

        val intent = Intent()
        avatar.setOnClickListener {
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Title"), PICK_IMAGE)

        }

        btnUpdate.setOnClickListener {
            saveInfo()
        }
    }

    fun readInfo(){
        databaseReference.get().addOnSuccessListener {
            if (it.exists()){
                etName.setText(it.child("name").value.toString())
                etAddress.setText(it.child("address").value.toString())
                etEmail.setText(it.child("email").value.toString())
            }
            else{
                parentFragmentManager.popBackStack()
            }
        }

        val localFile = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            avatar.setImageBitmap(bitmap)
            uri = Uri.parse(storageReference.toString())
            Log.d("a", uri.toString())
        }.addOnFailureListener{
            avatar.setImageResource(R.drawable.avatar_test)
        }
    }

    fun saveInfo(){
        databaseReference.setValue(userData(etName.text.toString(), etAddress.text.toString(), etEmail.text.toString()))
        storageReference.putFile(uri).addOnCompleteListener{
        }.addOnFailureListener{
            parentFragmentManager.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.data!!
                avatar?.setImageURI(uri)
            }

        }
        super.onResume()
    }
}