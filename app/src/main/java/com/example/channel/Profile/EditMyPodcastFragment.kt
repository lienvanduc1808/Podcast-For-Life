package com.example.channel.Profile

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import com.example.channel.NgheNgay.episodeData
import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.makeramen.roundedimageview.RoundedImageView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class EditMyPodcastFragment : Fragment() {
    private lateinit var btnAddAudio: Button
    private lateinit var imgBack: ImageView
    private lateinit var txtNewAlbumName: TextView
    private lateinit var txtNewEsposideName: TextView
    private lateinit var txtNewDes: TextView
    private lateinit var newImage: RoundedImageView
    private lateinit var txtChonDanhmuc: TextView
    private lateinit var spnAlbum: TextView
    private lateinit var etTitle4: EditText
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button

    private lateinit var etDescription4: EditText

    private lateinit var audioUri: String
    private lateinit var categorySource: String
    private lateinit var albumSource: String

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var currentUser: DatabaseReference

    private var mediaPlayer: MediaPlayer? = null
    // private var timer: ScheduledExecutorService? = null
    companion object {
        const val PICK_FILE = 99
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_my_podcast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categorySource = ""
        albumSource = ""
        auth = FirebaseAuth.getInstance()
        currentUser = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)
        databaseReference = FirebaseDatabase.getInstance().getReference("categories")
        turnBack(view)
        parentFragmentManager.setFragmentResultListener("send_item", this) { _, result ->

            parentFragmentManager.beginTransaction().show(this@EditMyPodcastFragment)
            val taskDanhmuc = result.getString("edt_danhmuc")
            val taskAlbumName = result.getString("edt_tenalbum")
            val taskTenTap = result.getString("edt_tentap")
            val taskDescription = result.getString("edt_mota")
            val taskUri = result.getString("edt_uri")
            Log.d("taskUri",taskUri.toString())
            val taskImg = result.getInt("edt_img")

            btnAddAudio = view.findViewById(R.id.btnAddAudio)
           // imgBack = view.findViewById(R.id.imgBack)
           // txtNewAlbumName = view.findViewById(R.id.txtNewAlbumName)
          //  txtNewEsposideName = view.findViewById(R.id.txtNewEsposideName)
          //  txtNewDes = view.findViewById(R.id.txtNewDes)
           // newImage = view.findViewById(R.id.newImage)

            txtChonDanhmuc = view.findViewById(R.id.txtChonDanhmuc)

            etTitle4 = view.findViewById(R.id.etTitle4)
            etDescription4 = view.findViewById(R.id.etDescription4)


            spnAlbum = view.findViewById(R.id.spnAlbum)
            btnDelete = view.findViewById(R.id.btnDelete)
            btnUpdate = view.findViewById(R.id.btnUpdate)




           // val txtNameUri = view.findViewById<TextView>(R.id.txtNameUri)





           // txtNameUri.setText(taskUri)
            txtChonDanhmuc.setText(taskDanhmuc)
            etTitle4.setText(taskTenTap)
            spnAlbum.setText(taskAlbumName)
            etDescription4.setText(taskDescription)


            chonDanhmuc()

            btnAddAudio.setOnClickListener {
                pickPodcastAudio()
            }


            btnUpdate.setOnClickListener {
                val database = FirebaseDatabase.getInstance()
                val ref = database.reference.child("categories")
                ref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (categorySnapshot in dataSnapshot.children) {
                            for (albumSnapshot in categorySnapshot.child("albums").children) {
                                for (episodeSnapshot in albumSnapshot.child("episodes").children) {
                                    if(taskUri.toString().equals(episodeSnapshot.key.toString())){


                                        var cateRef = categorySnapshot.key.toString()
                                        Log.d("cateRef",cateRef)
                                        var AlbumRef = albumSnapshot.key.toString()
                                        Log.d("AlbumRef",cateRef)
                                        var episoedeRef = albumSnapshot.child("episodes").ref
                                        val reafRef = "categories" +"/" +cateRef+"/albums/"+AlbumRef+"/episodes/"+ episodeSnapshot.key.toString()
                                        Log.d("RealRef",reafRef)
                                        val myRef = database.getReference(reafRef)
                                        val updates: MutableMap<String, Any> = HashMap()
                                        updates["title"] = etTitle4.text.toString()
                                        updates["descript"] = etDescription4.text.toString()

                                        myRef.updateChildren(updates)
                                        replaceFragment(MyPodcastFragment())




                                    }

                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle errors
                    }
                })
            }





            btnDelete.setOnClickListener {
                val database = FirebaseDatabase.getInstance()
                val ref = database.reference.child("categories")
                ref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (categorySnapshot in dataSnapshot.children) {
                            for (albumSnapshot in categorySnapshot.child("albums").children) {
                                for (episodeSnapshot in albumSnapshot.child("episodes").children) {
                                    if(taskUri.toString().equals(episodeSnapshot.key.toString())){


                                        var cateRef = categorySnapshot.key.toString()
                                        Log.d("cateRef",cateRef)
                                        var AlbumRef = albumSnapshot.key.toString()
                                        Log.d("AlbumRef",cateRef)
                                        var episoedeRef = albumSnapshot.child("episodes").ref
                                        val reafRef = "categories" +"/" +cateRef+"/albums/"+AlbumRef+"/episodes/"+ episodeSnapshot.key.toString()
                                        Log.d("RealRef",reafRef)
                                        val myRef = database.getReference(reafRef)
                                        myRef.removeValue()
                                        replaceFragment(MyPodcastFragment())




                                    }

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










    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddNewPodcastFragment.PICK_FILE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var uri: Uri = data.data!!
                createMediaPlayer(uri)
            }
        }
    }

    private fun turnBack(view: View) {
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            parentFragmentManager.popBackStack()


        }

    }
    private fun chonDanhmuc() {
        val popupMenu = PopupMenu(view?.context, txtChonDanhmuc)

        popupMenu.inflate(R.menu.popup_danhmuc)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAllCat -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_1"

                    true
                }
                R.id.menuNews -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_1"

                    true
                }
                R.id.menuSport -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_2"

                    true
                }
                R.id.menuComedy -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_3"

                    true
                }
                R.id.menuBusiness -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_4"

                    true
                }
                R.id.menuXH -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_5"

                    true
                }
                else -> false
            }
        }
        txtChonDanhmuc.setOnClickListener {
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


    private fun replaceFragment(fragment: Fragment){
        parentFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .hide(this@EditMyPodcastFragment)
            .commit()
    }

    private fun pickPodcastAudio() {
        val intent =  Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "audio/*"
        startActivityForResult(intent, AddNewPodcastFragment.PICK_FILE)
    }

    private fun createMediaPlayer(uri: Uri) {
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        val txtNameUri = view?.findViewById<TextView>(R.id.txtNameUri)
        try {
            mediaPlayer!!.setDataSource(requireContext(),uri)
            mediaPlayer!!.prepare()

            val cr = requireContext().contentResolver
            val fileName: String? = getFileName(uri, cr)
            val outputStream: FileOutputStream?
            if (fileName != null) {
                try {
                    outputStream = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)
                    val fileDescriptor: AssetFileDescriptor = cr.openAssetFileDescriptor(uri, "r")!!
                    val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (inputStream.read(buffer).also { length = it } > 0) {
                        outputStream.write(buffer, 0, length)
                    }
                    outputStream.close()
                    inputStream.close()
                    val filePath = requireContext().getFileStreamPath(fileName).absolutePath
                    txtNameUri?.text = filePath.toString()
                    audioUri = filePath.toString()
                    // Sử dụng đường dẫn tệp ở đây
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }catch (e: IOException) {
            txtNameUri?.text = e.toString()
        }


    }

    private fun getFileName(uri: Uri, cr: ContentResolver): String? {
        var fileName: String? = null
        val cursor = cr.query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            val cursorCol = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            fileName = cursor.getString(cursorCol)
        }
        return fileName
    }



}