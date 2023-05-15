package com.example.channel.admin



import android.content.Context
import android.os.Bundle
import android.util.Log


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity


import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.channel.*
import com.example.channel.NgheNgay.ItemViewHolder
import com.example.channel.Search.Album
import com.google.firebase.storage.FirebaseStorage



class XemTatCaAlbumAdapter(private val items: ArrayList<Album>, val context: Context) :
    RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album1, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.text1.text = item.name
        holder.text2.text = item.channel
        val storageRef = FirebaseStorage.getInstance().reference
        val logo = item.logo
        val imageRef = storageRef.child("Album/$logo")

        // Get the download URL of the image
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            // Use the URL to display the image
            Glide.with(context).load(uri).into(holder.image)
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e("FirebaseStorage", "Error getting download URL", exception)
        }

        holder.itemView.setOnClickListener{
            (context as AppCompatActivity).getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_frame_layout, AdminEditAlbumFragment())
                .addToBackStack(null)
                .commit()

            val send_data = Bundle().apply {
                val id_album = item.id_album.split(",")
                putString("idAlbum", id_album[0])
                putString("tendanhmuc", id_album[1])
                Log.d("idAlbum",item.id_album.toString())

            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)
        }
    }

    override fun getItemCount() = items.size

}