package com.example.channel.NgheNgay


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
import com.example.channel.R
import com.google.firebase.storage.FirebaseStorage

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text1: TextView = itemView.findViewById(R.id.tvAlbumName)
    val text2: TextView = itemView.findViewById(R.id.tvAlbumArtist)
    val image: ImageView = itemView.findViewById(R.id.ivLogo)

}

class XemTatCaAlbumAdapter(private val items: ArrayList<albumData>, val context: Context) :
    RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.text1.text = item.album_name
        holder.text2.text = item.channel
//        Glide.with(context).load(item.logo)
//            .into(holder.image)
        // Create a reference to the image file in Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference
        val logo = item.logo_album
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
                .replace(R.id.frame_layout, NgheNgayFragment())
                .addToBackStack(null)
                .commit()

            val send_data = Bundle().apply {
                putString("idAlbum", item.logo_album.toString())
                Log.d("idAlbum",item.logo_album.toString())

            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)
        }
    }

    override fun getItemCount() = items.size

}