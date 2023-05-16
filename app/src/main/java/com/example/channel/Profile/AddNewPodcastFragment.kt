package com.example.channel.Profile

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.channel.NgheNgay.albumData
import com.example.channel.NgheNgay.episodeData

import com.example.channel.Profile.NewAlbumFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.makeramen.roundedimageview.RoundedImageView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import com.example.channel.R
import java.time.LocalDate


class AddNewPodcastFragment : Fragment() {
    private lateinit var btnAddAudio: Button
    private lateinit var imgBack: ImageView


    private lateinit var txtXong: TextView
    private lateinit var txtChonDanhmuc: TextView
    private lateinit var spnAlbum: Spinner
    private lateinit var etTitle4: EditText
    private lateinit var etDescription4: EditText

    private lateinit var audioUri: String
    private lateinit var categorySource: String
    private lateinit var albumSource: String

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var currentUser: DatabaseReference

    // private var duration: String = ""
    private var mediaPlayer: MediaPlayer? = null
    // private var timer: ScheduledExecutorService? = null
    companion object {
        const val PICK_FILE = 99
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_new_podcast, container, false)

        btnAddAudio = view.findViewById(R.id.btnAddAudio)
        imgBack = view.findViewById(R.id.imgBack)


        txtXong = view.findViewById(R.id.txtXong)
        txtChonDanhmuc = view.findViewById(R.id.txtChonDanhmuc)
        spnAlbum = view.findViewById(R.id.spnAlbum)
        etTitle4 = view.findViewById(R.id.etTitle4)
        etDescription4 = view.findViewById(R.id.etDescription4)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        currentUser = FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)
        databaseReference = FirebaseDatabase.getInstance().getReference("categories")

        btnAddAudio.setOnClickListener {
            pickPodcastAudio()
        }

        imgBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        categorySource = ""
        albumSource = ""
        chonDanhmuc()

//        chonAlbum()
//        chonAlbum()

        txtXong.setOnClickListener {
            createEpisode()
            parentFragmentManager.popBackStack()
        }

    }

    //get uri audio from intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                var uri: Uri = data.data!!
                createMediaPlayer(uri)
            }
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
                    chonAlbum()
                    true
                }
                R.id.menuNews -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_1"
                    chonAlbum()
                    true
                }
                R.id.menuSport -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_2"
                    chonAlbum()
                    true
                }
                R.id.menuComedy -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_3"
                    chonAlbum()
                    true
                }
                R.id.menuBusiness -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_4"
                    chonAlbum()
                    true
                }
                R.id.menuXH -> {
                    txtChonDanhmuc.setText(menuItem.title.toString())
                    categorySource = "category_id_5"
                    chonAlbum()
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
    private fun chonAlbum() {
        if (categorySource == "")
            return
        val albumChoice = mutableListOf<String>()
        albumChoice.add("---------------------")
        albumChoice.add("Tạo Album mới")
        val albumKey = mutableListOf<String>()

        databaseReference.child(categorySource+"/albums")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                albumChoice.clear()
                albumChoice.add("---------------------")
                albumChoice.add("Tạo Album mới")
                val albumKey = mutableListOf<String>()
                for (albumSnapshot in snapshot.children)
                    if (auth.currentUser!!.uid.toString() in albumSnapshot.key.toString()){
                        albumChoice.add(albumSnapshot.child("album_name").value.toString())
                        albumKey.add(albumSnapshot.key.toString())
                    }
            }
            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
                Toast.makeText(requireContext(), "Can not get data", Toast.LENGTH_SHORT);
            }
        })

        val adt = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,albumChoice)
        spnAlbum.adapter = adt
        spnAlbum.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //here
                val selectedFragment = p0?.getItemAtPosition(position).toString()
                if (selectedFragment.trim() == "Tạo Album mới")
                    replaceFragment(NewAlbumFragment())
                else
                    if (albumKey.size >= 1 && position >= 2)
//                        Log.d("key", albumKey.size.toString() + " " + position.toString())
                        albumSource = albumKey[position - 2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
    private fun pickPodcastAudio() {
        val intent =  Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "audio/*"
        startActivityForResult(intent,PICK_FILE)
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
    private fun getNameFromUri(uri: Uri): Uri {
        var contentUri:Uri =MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var cursor: Cursor? = null
        cursor = requireContext().contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DISPLAY_NAME), null, null, null)
        if (cursor != null && cursor.moveToFirst()) {

            val columnIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            if(columnIndex>=0){
                val id = cursor.getLong(columnIndex)
            }

            contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id.toLong())
            cursor?.close()


        }
        return contentUri

    }

    private fun createEpisode(){
        databaseReference = databaseReference.child(categorySource + "/albums/" + albumSource + "/episodes/")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var count_episode: Int = 0
                if(dataSnapshot.childrenCount == null)
                    count_episode = 0
                else {
                    count_episode = dataSnapshot.childrenCount.toInt()
                    for (episodeSnapshot in dataSnapshot.children){
                        Log.d("num", episodeSnapshot.key.toString().lastIndexOf("ep").toString())
                        Log.d("num", episodeSnapshot.key.toString().substring(episodeSnapshot.key.toString().lastIndexOf("ep") + 2, episodeSnapshot.key.toString().length))
                        if(episodeSnapshot.key.toString().substring(episodeSnapshot.key.toString().lastIndexOf("ep") + 2, episodeSnapshot.key.toString().length).toInt() >= count_episode)
                            count_episode = episodeSnapshot.key.toString().substring(episodeSnapshot.key.toString().lastIndexOf("ep") + 2, episodeSnapshot.key.toString().length).toInt() + 1
                    }
                }

                var newEpisodeId: String = albumSource + "ep" + count_episode.toString()
                databaseReference.child(newEpisodeId).setValue(episodeData(etTitle4.text.toString(), etDescription4.text.toString(), "${LocalDate.now()}", albumSource))
                databaseReference.child(newEpisodeId).child("listener").setValue(0)
                storageReference = FirebaseStorage.getInstance().getReference("AudioEpisode/"+newEpisodeId)

                val file = Uri.fromFile(File(audioUri))
                storageReference.putFile(file)
                    .addOnSuccessListener {
                        // Handle success
                    }.addOnFailureListener {
                        // Handle failure
                    }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun replaceFragment(fragment: Fragment){
        parentFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .hide(this@AddNewPodcastFragment)
            .commit()
    }
}



