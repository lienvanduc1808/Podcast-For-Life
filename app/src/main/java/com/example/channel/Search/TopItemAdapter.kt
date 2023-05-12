package com.example.channel.Search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.channel.NgheNgay.NgheNgayFragment
import com.example.channel.R
import com.google.firebase.storage.FirebaseStorage


class TopItemAdapter(private val carouselDataList: ArrayList<Top_Item>, val context: Context) :
    RecyclerView.Adapter<TopItemAdapter.CarouselItemViewHolder>() {
    var onItemClick: ((Top_Item) -> Unit)? = null

    inner class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val albumNameTV = view.findViewById<TextView>(R.id.tvAlbumName)
        val albumArtistTV = view.findViewById<TextView>(R.id.tvAlbumArtist)
        val albumLogoIV = view.findViewById<ImageView>(R.id.ivLogo)
        //val rankingItem = view.findViewById<TextView>(R.id.txtRankingItem)
        init {
            view.setOnClickListener {
                onItemClick?.invoke(carouselDataList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking_chanel, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val album: Top_Item = carouselDataList[position]
        val albumNameTV = holder.albumNameTV
        albumNameTV.setText(album.name)
        val albumArtistTV = holder.albumArtistTV
        albumArtistTV.setText(album.channel)
        val logoIV = holder.albumLogoIV
        val storageRef = FirebaseStorage.getInstance().reference
        val logo = album.logo

        val imageRef = storageRef.child("Album/$logo")

        // Get the download URL of the image
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            // Use the URL to display the image
            Glide.with(context).load(uri).placeholder(R.drawable.img_17).into(holder.albumLogoIV)
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e("FirebaseStorage", "Error getting download URL", exception)
        }
//        val rankingItem = holder.rankingItem
//        if(album.totalListeners !=0){
//            //rankingItem.setText(album.totalListeners.toString())
//        }


        holder.itemView.setOnClickListener{
            (context as AppCompatActivity).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, NgheNgayFragment())
                .addToBackStack(null)
                .commit()

            val send_data = Bundle().apply {
                putString("idAlbum", album.id_album.toString())


            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)


        }
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}
