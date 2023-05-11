package com.example.channel.admin

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
import com.bumptech.glide.Glide
import com.example.channel.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

//class AdminEditCateFragment : Fragment() {
//    private lateinit var ivEditCateImg: ImageView
//    private lateinit var etEditCateName: EditText
//    private lateinit var btnEditCateImg: Button
//    private lateinit var btnUpdateCate: Button
//    private lateinit var backSign: ImageButton
//    private val PICK_IMAGE: Int = 3
//    private lateinit var uri: Uri
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_admin_edit_cate, container, false)
//        ivEditCateImg = view.findViewById(R.id.ivEditCateImg)
//        etEditCateName = view.findViewById(R.id.etCateName)
//        btnEditCateImg = view.findViewById(R.id.btnCateImg)
//        btnUpdateCate = view.findViewById(R.id.btnUpdateCate)
//        // Inflate the layout for this fragment
//        return view
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//        // Retrieve the category information from arguments
//        val cateName = arguments?.getString("cate_name")
//        val cateImage = arguments?.getString("cate_image")
//
//        etEditCateName.setText(cateName)
//
//        val database = FirebaseDatabase.getInstance()
//        val ref = database.reference.child("categories")
//        val storageRef = FirebaseStorage.getInstance().reference
//
//        backSign = view.findViewById(R.id.ibBackAddCate)
//        backSign.setOnClickListener {
//            parentFragmentManager!!.popBackStack()
//        }
//
//        backSign.setOnClickListener {
//            val intent = Intent()
//            intent.action = Intent.ACTION_GET_CONTENT
//            intent.type = "image/*"
//            startActivityForResult(Intent.createChooser(intent, "Title"), PICK_IMAGE)
//        }
//
//    }
//}


class AdminEditCateFragment : Fragment() {
    private lateinit var ivEditCateImg: ImageView
    private lateinit var etEditCateName: EditText
    private lateinit var btnEditCateImg: Button
    private lateinit var btnUpdateCate: Button
    private lateinit var backSign: ImageButton
    private val PICK_IMAGE: Int = 3
    private lateinit var uri: Uri
    private lateinit var storageReference: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_edit_cate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivEditCateImg = view.findViewById(R.id.ivEditCateImg)
        etEditCateName = view.findViewById(R.id.etCateName)
        btnEditCateImg = view.findViewById(R.id.btnCateImg)
        btnUpdateCate = view.findViewById(R.id.btnUpdateCate)
        backSign = view.findViewById(R.id.ibBackEditCate)

        val database = FirebaseDatabase.getInstance()
        val ref = database.reference.child("categories")
        val storageRef = FirebaseStorage.getInstance().reference

        backSign.setOnClickListener {
            parentFragmentManager?.popBackStack()
        }
        val cateName = arguments?.getString("cate_name")
        val cateImage = arguments?.getString("cate_image")
        Log.d("klq", "image: $cateImage")
        etEditCateName.setText(cateName)
        val imageRef = storageRef.child("Category/$cateImage")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            // Use the URL to display the image
            Glide.with(requireContext()).load(uri).into(ivEditCateImg)
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e("FirebaseStorage", "Error getting download URL", exception)
        }

        btnEditCateImg.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE)
        }
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (categorySnapshot in dataSnapshot.children) {
                        if(cateName.toString().equals(categorySnapshot.child("cate_name").value.toString())){
                            var cateRef = categorySnapshot.key.toString()

                            val reafRef = "categories" +"/" +cateRef

                            val myRef = database.getReference(reafRef)
                            btnUpdateCate.setOnClickListener {

                                val updates: MutableMap<String, Any> = HashMap()
                                updates["cate_name"] = etEditCateName.text.toString()
                                myRef.updateChildren(updates)
                                storageReference = FirebaseStorage.getInstance().getReference("Category/$cateImage")
                                Log.d("lk","nae: $etEditCateName")
                                storageReference.putFile(uri).addOnCompleteListener{
                                }.addOnFailureListener{
                                    parentFragmentManager.popBackStack()
                                }


                            }

                        }

                    }

                }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



//
//        btnUpdateCate.setOnClickListener {
//            // Perform the update category operation here
//            val categoryName = etEditCateName.text.toString().trim()
//
//            // Validate the category name
//            if (categoryName.isEmpty()) {
//                Toast.makeText(requireContext(), "Please enter a category name", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            val updates: MutableMap<String, Any> = HashMap()
//            updates["album_name"] = categoryName
//
//
//            val storageReference = FirebaseStorage.getInstance().getReference("Category/$cateImage")
//            val database = FirebaseDatabase.getInstance()
//            val reafRef = "categories" +"/" +cateRef+"/
//            val myRef = database.getReference(reafRef)
//
//            myRef.updateChildren(updates)
//
//            storageReference.putFile(uri).addOnCompleteListener{
//            }.addOnFailureListener{
//                parentFragmentManager.popBackStack()
//            }
//
//
}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            // Get the selected image URI
            uri = data.data!!

            // Set the selected image to the ImageView
            ivEditCateImg.setImageURI(uri)
        }
    }
}

