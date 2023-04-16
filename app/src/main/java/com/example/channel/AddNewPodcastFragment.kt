package com.example.channel

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.core.net.toUri
import androidx.fragment.app.Fragment

import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.NonDisposableHandle.parent

import java.io.IOException





class AddNewPodcastFragment : Fragment() {




   // private var duration: String = ""
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
        return inflater.inflate(R.layout.fragment_add_new_podcast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val txtXong = view.findViewById<TextView>(R.id.txtXong)
        txtXong.setOnClickListener {

        }



        turnBack(view)
        chonAlbum(view)
        chonDanhmuc(view)
        pickPodcastAudio(view)

        parentFragmentManager.setFragmentResultListener("xong", this) { _, result ->

            parentFragmentManager.beginTransaction().show(this@AddNewPodcastFragment)
            val taskAlbumName = result.getString("task_tenAlbum")
            val taskTenTap = result.getString("task_tenTap")
            val taskDescription = result.getString("task_description")
            val taskUri = result.getString("task_uri")




            val txtNewAlbumName = view.findViewById<TextView>(R.id.txtNewAlbumName)
            val txtNewEsposideName =  view.findViewById<TextView>(R.id.txtNewEsposideName)
            val txtNewDes = view.findViewById<TextView>(R.id.txtNewDes)
            val newImage =   view.findViewById<RoundedImageView>(R.id.newImage)

            if (taskAlbumName != null && taskTenTap != null && taskDescription != null && taskUri != null) {

                txtNewAlbumName.setText(taskAlbumName)
                txtNewEsposideName?.setText(taskTenTap)
                txtNewDes?.setText(taskDescription)
                newImage?.setImageURI(taskUri.toUri())

            }

        }











    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                val uri: Uri = data.data!!
                createMediaPlayer(uri)
            }
        }








    }




    private fun chonAlbum(view: View) {
        val items = mutableListOf<String>()
        items.add("-------------------------------------------------------------")
        items.add("Tạo Album mới")
        items.add("Thêm vào Album đã có")

        val adt = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,items)
        val spnAlbum  = view.findViewById<Spinner>(R.id.spnAlbum)
        spnAlbum.adapter = adt
        spnAlbum.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                    val selectedFragment = p0?.getItemAtPosition(p2).toString()

                    when (selectedFragment) {

                        "Tạo Album mới" -> {
                            val fragment = NewAlbumFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.frame_layout, fragment)

                                .addToBackStack(null)
                                .hide(this@AddNewPodcastFragment)
                                .commit()


                        }
                        "Thêm vào Album đã có" -> {
                            val fragment = ExistAlbumFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.frame_layout, fragment)

                                .addToBackStack(null)
                                .hide(this@AddNewPodcastFragment)
                                .commit()
                        }


                    }





//
            }


            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }


















































    private fun pickPodcastAudio(view: View) {
        val btnAddAudio = view.findViewById<Button>(R.id.btnAddAudio)
        btnAddAudio.setOnClickListener {
            val intent =  Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "audio/*"
            startActivityForResult(intent,PICK_FILE)

        }
    }











































    private fun turnBack(view: View) {
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
//                if(fragmentManager!=null){
//                    fragmentManager?.popBackStack()
//
//                }
            //nhớ phải add fragment này vào backStack thì mới dùng được
            //https://www.youtube.com/watch?v=b9a3-gZ9CGc

        }

    }



















    private fun chonDanhmuc(view: View) {
        val txtShowList = view.findViewById<TextView>(R.id.txtChonDanhmuc)
        val popupMenu = PopupMenu(view?.context, txtShowList)

        popupMenu.inflate(R.menu.popup_danhmuc)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAllCat -> {

                    txtShowList.setText(menuItem.title.toString())


                    true
                }
                R.id.menuXH -> {
                    txtShowList.setText(menuItem.title.toString())

                    true
                }
                R.id.menuNews -> {
                    txtShowList.setText(menuItem.title.toString())

                    true
                }
                R.id.menuComedy -> {
                    txtShowList.setText(menuItem.title.toString())

                    true
                }
                R.id.menuBusiness -> {
                    txtShowList.setText(menuItem.title.toString())

                    true
                }
                R.id.menuSport -> {
                    txtShowList.setText(menuItem.title.toString())

                    true
                }
                else -> false
            }
        }
        txtShowList.setOnClickListener {
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

            txtNameUri?.text = getNameFromUri(uri)

        }catch (e: IOException) {
            txtNameUri?.text = e.toString()
        }


    }

    private fun getNameFromUri(uri: Uri): String? {
        var fileName = ""
        var cursor: Cursor? = null
        cursor = requireContext().contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DISPLAY_NAME), null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME).toInt())
        }
        cursor?.close()
        return fileName
    }





}


