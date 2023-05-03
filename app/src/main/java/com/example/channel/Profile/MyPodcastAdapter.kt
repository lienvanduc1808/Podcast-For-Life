package com.example.channel.Profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.channel.R
import com.google.firebase.storage.FirebaseStorage

class MyPodcastAdapter(context: Context, resource: Int, list: List<MyPodCastData>):
    ArrayAdapter<MyPodCastData>(context, resource, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.my_podcast_item, parent, false)
        }

        val currentItem = getItem(position)

        val ten_Album = rowView?.findViewById<TextView>(R.id.txtTenAlbum)

        val ten_Danh_Muc = rowView?.findViewById<TextView>(R.id.txtDanhMuc)
        val ten_tap = rowView?.findViewById<TextView>(R.id.txtTenTap)
        val mo_ta = rowView?.findViewById<TextView>(R.id.txtMota)
        val img_tap = rowView?.findViewById<ImageView>(R.id.imgAnhTap)
        val uri_podcast = rowView?.findViewById<TextView>(R.id.txtUri)


        ten_Album?.text =currentItem?.ten_album
        ten_tap?.text =currentItem?.ten_tap
        mo_ta?.text = currentItem?.mo_ta
        ten_Danh_Muc?.text = currentItem?.danh_muc
        val storage = FirebaseStorage.getInstance().getReference("Album/")
        val imageRef = storage.child(currentItem?.img_podcast.toString())
        var imageUrl:String=""

        imageRef.downloadUrl.addOnSuccessListener { uri ->
            imageUrl = uri.toString()
            Glide.with(context)
                .load(imageUrl)
                .into(img_tap!!)


            // sử dụng đường dẫn của ảnh ở đây
        }.addOnFailureListener { exception ->
            // Xử lý lỗi tại đây
        }

     //   uri_podcast?.text = currentItem?.uri_podcast





        return rowView!!
    }
}