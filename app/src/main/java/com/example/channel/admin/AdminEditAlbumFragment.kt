package com.example.channel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.channel.admin.XemTatCaAlbumAdapter
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AdminEditAlbumFragment : Fragment() {

    private lateinit var imgBack:ImageView
    private lateinit var txtAlbumName : TextView
    private lateinit var txtAlbum : EditText
    private lateinit var etDescription4 : EditText
    private lateinit var btnImage : Button
    private lateinit var imageView : ImageView
    private lateinit var btnDelete :Button
    private lateinit var btnUpdate :Button
    private val PICK_IMAGE: Int = 3
    private lateinit var uri: Uri
    private lateinit var storageReference: StorageReference



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_edit_album, container, false)




        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtAlbumName = view.findViewById(R.id.txtAlbumName)
        txtAlbum = view.findViewById(R.id.txtAlbum)
        etDescription4 = view.findViewById(R.id.etDescription4)
        btnImage = view.findViewById(R.id.btnImage)
        imageView = view.findViewById(R.id.imageView)
        btnDelete = view.findViewById(R.id.btnDelete)
        btnUpdate = view.findViewById(R.id.btnUpdate)

        val database = FirebaseDatabase.getInstance()
        val ref = database.reference.child("categories")
        val storageRef = FirebaseStorage.getInstance().reference

        imgBack = view.findViewById(R.id.imgBack)


        btnImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Title"), PICK_IMAGE)
        }

        parentFragmentManager.setFragmentResultListener("send_idAlbum", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@AdminEditAlbumFragment)


            val idAlbum = result.getString("idAlbum")

            imgBack.setOnClickListener {
                val taskDanhmuc = result.getString("tendanhmuc")
                val send_data = Bundle().apply {
                    putString("tendanhmuc", taskDanhmuc)
                }
                (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_dm", send_data)
                parentFragmentManager!!.popBackStack()

            }
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (categorySnapshot in dataSnapshot.children) {
                        for (albumSnapshot in categorySnapshot.child("albums").children) {

                                if(idAlbum.toString().equals(albumSnapshot.key.toString())){


                                    var cateRef = categorySnapshot.key.toString()
                                    val cateName = categorySnapshot.child("cate_name").value.toString()
                                    txtAlbumName.setText(cateName)
                                    val AlbumName = albumSnapshot.child("album_name").value.toString()
                                    txtAlbum.setText(AlbumName)
                                    val Albumdes = albumSnapshot.child("description").value.toString()
                                    etDescription4.setText(Albumdes)
                                    val imageRef = storageRef.child("Album/$idAlbum")
                                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                                        // Use the URL to display the image
                                        Glide.with(requireContext()).load(uri).into(imageView)
                                    }.addOnFailureListener { exception ->
                                        // Handle any errors
                                        Log.e("FirebaseStorage", "Error getting download URL", exception)
                                    }


                                    var AlbumRef = albumSnapshot.key.toString()
                                    Log.d("AlbumRef",cateRef)


                                    val reafRef = "categories" +"/" +cateRef+"/albums/"+AlbumRef
                                    Log.d("RealRef",reafRef)





                                    val myRef = database.getReference(reafRef)
                                    btnUpdate.setOnClickListener {

                                          val updates: MutableMap<String, Any> = HashMap()
                                        updates["album_name"] = txtAlbum.text.toString()
                                         updates["description"] = etDescription4.text.toString()

                                        storageReference = FirebaseStorage.getInstance().getReference("Album/$idAlbum")


                                        myRef.updateChildren(updates)

                                        storageReference.putFile(uri).addOnCompleteListener{
                                        }.addOnFailureListener{
                                            parentFragmentManager.popBackStack()
                                        }


                                        Toast.makeText(requireContext(), "Update successfully", Toast.LENGTH_SHORT).show()
                                       // replaceFragment(ItemAlbumManageFragment())

                                    }

                                    btnDelete.setOnClickListener {
                                        myRef.removeValue()
                                        parentFragmentManager.popBackStack()
                                       // replaceFragment(ItemAlbumManageFragment())

                                    }

                                   //




                                }

                            }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            })
















        }












    }

    private fun replaceFragment(fragment: Fragment){
        parentFragmentManager
            .beginTransaction()
            .add(R.id.admin_frame_layout, fragment)
            .addToBackStack(null)
            .hide(this@AdminEditAlbumFragment)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.data!!
                imageView?.setImageURI(uri)
//                uriData = uri.toString()
            }
        }
        super.onResume()
    }











    }




