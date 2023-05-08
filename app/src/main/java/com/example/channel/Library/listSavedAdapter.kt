package com.example.channel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.channel.NgheNgay.EpisodeBottomSheet
import com.example.channel.NgheNgay.episodeData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.makeramen.roundedimageview.RoundedImageView
class ListSavedAdapter(context: Context, resource: Int, objects: MutableList<episodeData>):
    ArrayAdapter<episodeData>(context, resource, objects) {

    private lateinit var storageReference: StorageReference
    private val popupWindows = mutableMapOf<Int, PopupWindow>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_episode_saved, parent, false)
        }

        var popupWindow: PopupWindow? = null
        val currentItem = getItem(position)

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

        val itemDescript = itemView?.findViewById<TextView>(R.id.descriptOpisode)
        itemDescript?.text = currentItem?.descript

        //Popup
        val itemButtonMoreHoriz = itemView?.findViewById<ImageButton>(R.id.ibMoreHoriz)
        itemButtonMoreHoriz?.setOnClickListener{

            if(popupWindow == null){
                val popupView = LayoutInflater.from(context).inflate(R.layout.popup_more_horiz_saved, null)
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
//                val clDownloadEpisode = itemView?.findViewById<ConstraintLayout>(R.id.clDownloadEpisode)
//                val clGotoChannel = itemView?.findViewById<ConstraintLayout>(R.id.clGotoChannel)
//                val clUnSaved = itemView?.findViewById<ConstraintLayout>(R.id.clUnSaved)
//
//                var test = itemView?.findViewById<TextView>(R.id.test)
//                clDownloadEpisode?.setOnClickListener{
//                    Toast.makeText(itemView?.context, "Download", Toast.LENGTH_SHORT).show()
//                    test?.text = "CCCCCC"
//                    Log.i("adfasd", "adfasdfadsf")
//                }
//
//                clGotoChannel?.setOnClickListener{
//                    Toast.makeText(context, "Goto", Toast.LENGTH_SHORT).show()
//
//                }
//
//                clUnSaved?.setOnClickListener{
//                    Toast.makeText(context, "Unsaved", Toast.LENGTH_SHORT).show()
//
//                }
            }

        }

        //open bottom sheet play episode
        var episodeBS = EpisodeBottomSheet()
        val ibPlayEpisode = itemView?.findViewById<ImageButton>(R.id.ibPlayEpisode)

        ibPlayEpisode?.setOnClickListener{
            //Mở tập podcast và đổi podcast thu nhỏ hiện tại
            episodeBS.show((context as AppCompatActivity).getSupportFragmentManager(), "Episode screen")
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