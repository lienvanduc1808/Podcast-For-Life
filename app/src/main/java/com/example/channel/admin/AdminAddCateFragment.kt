package com.example.channel.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment

import com.example.channel.R
import com.example.channel.Search.DanhMuc
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminAddCateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminAddCateFragment : Fragment() {
    private var cateNameET: EditText? = null
    private var addCateBtn: Button? = null
    private lateinit var btnImage: Button
    private lateinit var ibBack: ImageButton
    private val PICK_IMAGE: Int = 3
    private lateinit var uri: Uri
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_admin_add_cate, container, false)
        cateNameET = view.findViewById(R.id.addCateNameET)
        addCateBtn = view.findViewById(R.id.buttonAddCate)
        btnImage = view.findViewById(R.id.btnCateImg)
        ibBack = view.findViewById(R.id.ibBackAddCate)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Title"), PICK_IMAGE)
        }
        ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val database = Firebase.database
        val reference = database.getReference("categories")
        val key = reference.push().key as String

        addCateBtn!!.setOnClickListener {
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    reference.child(key).setValue(DanhMuc(cateNameET!!.text.toString(),key))
                    storageReference = FirebaseStorage.getInstance().getReference("Category/$key")
                    storageReference.putFile(uri).addOnCompleteListener{
                    }.addOnFailureListener{
                        parentFragmentManager.popBackStack()
                    }

                    parentFragmentManager.popBackStack()
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val imgView = view?.findViewById<ImageView>(R.id.cateImgIV)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.data!!
                imgView?.setImageURI(uri)
            }
        }
        super.onResume()
    }
}