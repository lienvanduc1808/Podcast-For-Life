package com.example.channel.Profile

import android.app.Activity
import android.content.Intent
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

class InfoFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    private val PICK_IMAGE: Int = 3
    var uriData:String=""

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

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val intent = Intent()

        avatar = view.findViewById(R.id.avatar)
        avatar.setOnClickListener {
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Title"), PICK_IMAGE)

        }

        //get currentUser
        auth = FirebaseAuth.getInstance()

        //get node child(uid) của users
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)

        etName = view.findViewById(R.id.etName)
        etAddress = view.findViewById(R.id.etAddress)
        etEmail = view.findViewById(R.id.etEmail)

        getData()
        btnUpdate = view.findViewById(R.id.btnUpdate)
        btnUpdate.setOnClickListener {
            val updateInfo = userData(etName.text.toString(), etAddress.text.toString(), etEmail.text.toString())
            var imageUri = Uri.parse(uriData)
            databaseReference.setValue(updateInfo)
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

    fun getData(){
        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Lấy dữ liệu từ dataSnapshot

                val ds: DataSnapshot = dataSnapshot.child(auth.currentUser?.uid!!)// get the DataSnapshot for "b"
                val dsName = ds.child("name").value.toString()
                val dsEmail = ds.child("email").value.toString()
                val dsAddress = ds.child("address").value.toString()

                etName.setText(dsName)
                etAddress.setText(dsAddress)
                etEmail.setText(dsEmail)
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi khi không thể lấy dữ liệu
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                val uri: Uri = data.data!!
                avatar?.setImageURI(uri)
                uriData = uri.toString()

            }

        }
        super.onResume()
    }


}