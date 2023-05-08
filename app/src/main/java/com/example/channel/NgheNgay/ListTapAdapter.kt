package com.example.channel.NgheNgay


import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import com.example.channel.R
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class ListTapAdapter(context: Context, resource: Int,  list: List<ListTapData>):
    ArrayAdapter<ListTapData>(context, resource, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.list_tap, parent, false)
        }

        val currentItem = getItem(position)

        val auth: FirebaseAuth
        val storageReference: StorageReference
        val databaseReference: DatabaseReference

        auth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference("AudioEpisode/")
        databaseReference = FirebaseDatabase.getInstance().getReference("users/"+auth.currentUser?.uid)

        val ngayThang = rowView?.findViewById<TextView>(R.id.txtThoiGian)
        val tenTap = rowView?.findViewById<TextView>(R.id.txtTenTap)
        val phut = rowView?.findViewById<TextView>(R.id.txtPhut)
        val imgBtnPlay = rowView?.findViewById<ImageButton>(R.id.imgBtnPlay)
        val imgbtnMore = rowView?.findViewById<ImageButton>(R.id.imgBtnMore)
        imgBtnPlay?.setOnClickListener {

        }

        //popup menu cho imgbutton more
        val popupMenu = PopupMenu(rowView?.context, imgbtnMore)

        popupMenu.inflate(R.menu.menu_popup)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSave -> {
                    databaseReference.get().addOnSuccessListener {
                        if (it.exists()){
                            databaseReference.child("saved").push().setValue("zfOmRg42BEYgMyi0P2SyT9fJ4S52alb16ep1")
                        }
                    }
                    true
                }
                R.id.menuDownload -> {
                    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    val fileAudio = storageReference.child("ep3")
                    fileAudio.downloadUrl.addOnSuccessListener { uri ->
                        // Khởi tạo yêu cầu tải xuống
                        val request = DownloadManager.Request(uri)
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        request.setTitle("My MP3 File")
                        request.setDescription("Downloading")
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        val musicDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "Podcast")
                        if (!musicDirectory.exists()) {
                            musicDirectory.mkdirs()
                        }
//                        val filePath = File(musicDirectory, "my1.mp3").path
//                        Log.i("di", filePath.toString())
//                        request.setDestinationUri(Uri.parse("file://$filePath"))
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, "my.mp3")
                        // Thực hiện yêu cầu tải xuống
                        val downloadId = downloadManager.enqueue(request)
                    }

                    true
                }
                R.id.menuShare -> {

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


        ngayThang?.text = currentItem?.ngay_thang
        tenTap?.text = currentItem?.ten_tap
        phut?.text = currentItem?.phut



        return rowView!!


    }


}