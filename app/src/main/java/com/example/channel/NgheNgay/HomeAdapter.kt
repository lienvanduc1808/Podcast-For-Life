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
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.example.channel.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class HomeAdapter(private val carouselDataList: ArrayList<albumData>, val context: Context) :
    RecyclerView.Adapter<HomeAdapter.CarouselItemViewHolder>() {
    var onItemClick: ((albumData) -> Unit)? = null

    inner class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val albumNameTV = view.findViewById<TextView>(R.id.tvAlbumName)
        val albumArtistTV = view.findViewById<TextView>(R.id.tvAlbumArtist)
        val albumLogoIV = view.findViewById<ImageView>(R.id.ivLogo)
        init {
            view.setOnClickListener {
                onItemClick?.invoke(carouselDataList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)

        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val album: albumData = carouselDataList[position]
        val albumNameTV = holder.albumNameTV
        albumNameTV.setText(album.album_name)
        val albumArtistTV = holder.albumArtistTV
//        albumArtistTV.setText(album.channel)
        val userReference = Firebase.database.getReference("users")
        userReference.child(album.channel).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                albumArtistTV.setText(dataSnapshot.child("name").value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("eror", "Failed to read value.", error.toException())
            }
        })

        val storageRef = FirebaseStorage.getInstance().reference
        val logo = album.logo_album
        val imageRef = storageRef.child("Album/$logo")

        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(context).load(uri).into(holder.albumLogoIV)
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
                putString("idAlbum", album.logo_album.toString())
                Log.d("idAlbum",album.logo_album.toString())

            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)

        }
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}
