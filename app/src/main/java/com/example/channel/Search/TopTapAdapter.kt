package com.example.channel.Search


import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.channel.NgheNgay.EpisodeBottomSheet
import com.example.channel.NgheNgay.ListTapData
import com.example.channel.NgheNgay.episodeData
import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class TopTapAdapter(context: Context, resource: Int, list: ArrayList<ListTapData>):
    ArrayAdapter<ListTapData>(context, resource, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.top_tap, parent, false)
        }

        val currentItem = getItem(position)

        val time_top_tap = rowView?.findViewById<TextView>(R.id.txtTimeRankingTap)
        val ten_top_tap = rowView?.findViewById<TextView>(R.id.txtNameRankingTap)

        val img_top_tap = rowView?.findViewById<ImageView>(R.id.imgTopTap)
        val imgbtnMore = rowView?.findViewById<ImageButton>(R.id.imgBtnMoreRankingTap)

        val auth: FirebaseAuth
        val databaseReference: DatabaseReference

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("users/"+auth.currentUser?.uid)


        //popup menu cho imgbutton more
        val popupMenu = PopupMenu(rowView?.context, imgbtnMore)

        popupMenu.inflate(R.menu.ranking_tap)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuPlay->{

                    EpisodeBottomSheet().show((context as AppCompatActivity).getSupportFragmentManager(), "Episode screen")
                    val send_data = Bundle().apply {
                        putString("idEpisode", currentItem?._id.toString())
                        putString("dateEpisode", currentItem?.date.toString())
                        putString("titleEpisode", currentItem?.title.toString())
                        putString("descriptEpisode", currentItem?.descript.toString())
                        putString("imgEpisode", currentItem?.img.toString())
                        Log.d("idEpisode", currentItem?._id.toString())
//                putString("position", position.toString())
                    }
                    (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idEpisode", send_data)

                    true
                }
                R.id.menuSave -> {
                    databaseReference.get().addOnSuccessListener {
                    if (it.exists()){
                        databaseReference.child("saved").push().setValue(currentItem?._id.toString())
                    }
                }
                    true
                }
                R.id.menuDownload -> {
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
                    true
                }

                else -> false
            }
        }

        imgbtnMore?.setOnClickListener {

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


        time_top_tap?.text = currentItem?.listener.toString() + " luợt nghe"
        ten_top_tap?.text = currentItem?.title

        val storageRef = FirebaseStorage.getInstance().reference
        val logo = currentItem?.img

        val imageRef = storageRef.child("Album/$logo")

        // Get the download URL of the image
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            // Use the URL to display the image
            Glide.with(context).load(uri).placeholder(R.drawable.img_17).into(img_top_tap!!)
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e("FirebaseStorage", "Error getting download URL", exception)
        }



        return rowView!!
    }
}