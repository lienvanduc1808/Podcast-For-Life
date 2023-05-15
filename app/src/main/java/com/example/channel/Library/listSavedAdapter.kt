package com.example.channel.Library

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.channel.NgheNgay.EpisodeBottomSheet
import com.example.channel.NgheNgay.ListTapData
import com.example.channel.NgheNgay.NgheNgayFragment
import com.example.channel.NgheNgay.episodeData
import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ListSavedAdapter(context: Context, resource: Int, objects: MutableList<ListTapData>):
    ArrayAdapter<ListTapData>(context, resource, objects) {

    private lateinit var storageReference: StorageReference
    private lateinit var popupView: View
    private val popupWindows = mutableMapOf<Int, PopupWindow>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_episode_saved, parent, false)
        }

        var popupWindow: PopupWindow? = null
        val currentItem = getItem(position)

        val auth: FirebaseAuth
        val storageReference: StorageReference
        val userDatabase: DatabaseReference
        val episodeDatabase: DatabaseReference

        auth = FirebaseAuth.getInstance()
        userDatabase = FirebaseDatabase.getInstance().getReference("users/${auth.currentUser?.uid}")
//        episodeDatabase = FirebaseDatabase.getInstance().getReference("categories/")

        val itemImg = itemView?.findViewById<ImageView>(R.id.rivCover)

        storageReference =  FirebaseStorage.getInstance().getReference("Album/${currentItem?.img}")

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Log.i("url", uri.toString())
            Glide.with(itemView!!.context).load(uri).into(itemImg!!)
        }.addOnFailureListener { exception ->
            // Xử lý khi không lấy được link URL của ảnh
        }

        val itemDate = itemView?.findViewById<TextView>(R.id.dateUpload)
        itemDate?.text = currentItem?.date

        val itemName = itemView?.findViewById<TextView>(R.id.nameEpisode)
        itemName?.text = currentItem?.title

        val itemDescript = itemView?.findViewById<TextView>(R.id.descriptEpisode)
        itemDescript?.text = currentItem?.descript

        //Popup
        val itemButtonMoreHoriz = itemView?.findViewById<ImageButton>(R.id.ibMoreHoriz)
        itemButtonMoreHoriz?.setOnClickListener{

            if(popupWindow == null){
                popupView = LayoutInflater.from(context).inflate(R.layout.popup_more_horiz_saved, null)
                popupWindow = PopupWindow(popupView,800, ViewGroup.LayoutParams.WRAP_CONTENT)

                popupWindow?.setOnDismissListener {
                    popupWindow = null
                }
                popupWindows[position] = popupWindow!!
            }
            if (popupWindows[position]?.isShowing == true) {
                popupWindows[position]?.dismiss()
            } else {
                popupWindows[position]?.showAsDropDown(itemButtonMoreHoriz, 0, 30)
                val clDownloadEpisode = popupView?.findViewById<ConstraintLayout>(R.id.clDownloadEpisode)
                val clGotoChannel = popupView?.findViewById<ConstraintLayout>(R.id.clGotoChannel)
                val clUnSaved = popupView?.findViewById<ConstraintLayout>(R.id.clUnSaved)

                clDownloadEpisode?.setOnClickListener{
                    val path = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    )
                    val folder = File(path, "podcast4life")
                    folder.mkdirs()
                    val localFile = File(folder, currentItem?.title.toString() + ".mp3")
                    localFile.createNewFile()
                    val audioReference = FirebaseStorage.getInstance().reference.child("AudioEpisode/" + currentItem?._id.toString())
                    audioReference.getFile(localFile)
                    Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show()
                    popupWindows[position]?.dismiss()
                }

                clGotoChannel?.setOnClickListener{
                    Toast.makeText(context, "Goto", Toast.LENGTH_SHORT).show()
                    (context as AppCompatActivity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, NgheNgayFragment())
                        .addToBackStack(null)
                        .commit()

                    val send_data = Bundle().apply {
                        putString("idAlbum", currentItem?.img.toString())
                        Log.d("idAlbum", currentItem?.img.toString())

                    }
                    (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)

                }

                clUnSaved?.setOnClickListener{
                    Toast.makeText(context, "Unsaved", Toast.LENGTH_SHORT).show()
                    val _id = currentItem?._id
                    val saved = userDatabase.child("saved")
                    saved.orderByValue().equalTo(_id.toString()).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val nodeToDelete = dataSnapshot.children.iterator().next()
                                nodeToDelete.ref.removeValue().addOnSuccessListener {
                                    popupWindows[position]?.dismiss()
                                }.addOnFailureListener {

                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Xử lý lỗi nếu có
                        }
                    })
                }
            }

        }

        //open bottom sheet play episode
        var episodeBS = EpisodeBottomSheet()
        val ibPlayEpisode = itemView?.findViewById<ImageButton>(R.id.ibPlayEpisode)

        ibPlayEpisode?.setOnClickListener{
            //Mở tập podcast và đổi podcast thu nhỏ hiện tại

            val send_data = Bundle().apply {
                putString("idEpisode", currentItem?._id.toString())
                putString("dateEpisode", currentItem?.date.toString())
                putString("titleEpisode", currentItem?.title.toString())
                putString("descriptEpisode", currentItem?.descript.toString())
                putString("imgEpisode", currentItem?.img.toString())
                Log.d("idEpisode", currentItem?._id.toString())

            }
            episodeBS.show((context as AppCompatActivity).getSupportFragmentManager(), "Episode screen")
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idEpisode", send_data)
        }

        return itemView!!
    }

    fun dismissAllPopups() {
        for (popupWindow in popupWindows.values) {
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
            }
        }
    }
}