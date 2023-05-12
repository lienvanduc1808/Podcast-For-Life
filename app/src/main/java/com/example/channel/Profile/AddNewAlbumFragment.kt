package com.example.channel.Profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.channel.NgheNgay.albumData
import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.values
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class NewAlbumFragment : Fragment() {
    private lateinit var ivBack5: ImageView
    private lateinit var tvDone5: TextView
    private lateinit var edtName5: EditText
    private lateinit var tvChoiceCategory: TextView
    private lateinit var edtDescription5: EditText
    private lateinit var btnImage: Button

    private val PICK_IMAGE: Int = 3
    private lateinit var uri: Uri
    private var sum_album: Int = 0

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var currentUser: DatabaseReference



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_new_album, container, false)
        ivBack5 = view.findViewById(R.id.ivBack5)
        tvDone5 = view.findViewById(R.id.tvDone5)
        edtName5 = view.findViewById(R.id.edtName5)
        tvChoiceCategory = view.findViewById(R.id.tvChoiceCategory)
        edtDescription5 = view.findViewById(R.id.edtDescription5)
        btnImage = view.findViewById(R.id.btnImage)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        currentUser = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)
        currentUser.get().addOnSuccessListener {
            sum_album = it.child("sumalbum").value.toString().toInt()
        }

        choiceCategory()

        btnImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Title"), PICK_IMAGE)
        }
        ivBack5.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val userId = currentUser.key.toString()
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users").child(userId)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userName = dataSnapshot.child("name").value as String
                // sử dụng userName ở đây
                tvDone5.setOnClickListener {
                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var newAlbumId: String = currentUser.key.toString() + "alb" +sum_album.toString()

                            databaseReference.child(newAlbumId).setValue(albumData(edtName5.text.toString(), userName, edtDescription5.text.toString(), newAlbumId))
                            storageReference = FirebaseStorage.getInstance().getReference("Album/"+newAlbumId)

//                        val file = Uri.fromFile(File(uriData))
//                        storageReference.putFile(file)
//                            .addOnSuccessListener {
//                                // Handle success
//                            }.addOnFailureListener {
//                                // Handle failure
//                            }

                            storageReference.putFile(uri).addOnCompleteListener{
                            }.addOnFailureListener{
                                parentFragmentManager.popBackStack()
                            }

                            currentUser.child("sumalbum").setValue(sum_album + 1)
                            parentFragmentManager.popBackStack()
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // xử lý lỗi ở đây (nếu có)
            }
        })



    }
    private fun choiceCategory() {
        val popupMenu = PopupMenu(view?.context, tvChoiceCategory)
        popupMenu.inflate(R.menu.popup_danhmuc)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAllCat -> {
                    tvChoiceCategory.setText(menuItem.title.toString())
                    databaseReference = FirebaseDatabase.getInstance().getReference("categories/category_id_1/albums")
                    true
                }
                R.id.menuNews -> {
                    tvChoiceCategory.setText(menuItem.title.toString())
                    databaseReference = FirebaseDatabase.getInstance().getReference("categories/category_id_1/albums")
                    true
                }
                R.id.menuSport -> {
                    tvChoiceCategory.setText(menuItem.title.toString())
                    databaseReference = FirebaseDatabase.getInstance().getReference("categories/category_id_2/albums")
                    true
                }
                R.id.menuComedy -> {
                    tvChoiceCategory.setText(menuItem.title.toString())
                    databaseReference = FirebaseDatabase.getInstance().getReference("categories/category_id_3/albums")
                    true
                }
                R.id.menuBusiness -> {
                    tvChoiceCategory.setText(menuItem.title.toString())
                    databaseReference = FirebaseDatabase.getInstance().getReference("categories/category_id_4/albums")
                    true
                }
                R.id.menuXH -> {
                    tvChoiceCategory.setText(menuItem.title.toString())
                    databaseReference = FirebaseDatabase.getInstance().getReference("categories/category_id_5/albums")
                    true
                }
                else -> false
            }
        }
        tvChoiceCategory.setOnClickListener {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)

            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                popupMenu.show()
            }
            true
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val imgView = view?.findViewById<ImageView>(R.id.imageView)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.data!!
                imgView?.setImageURI(uri)
//                uriData = uri.toString()
            }
        }
        super.onResume()
    }
}