package com.example.channel.NgheNgay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.channel.R

class ItemDanhMucAdapter(private val carouselDataList: ArrayList<albumData>, val context: Context) :RecyclerView.Adapter<ItemDanhMucAdapter.CarouselItemViewHolder>()  {
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
        albumArtistTV.setText(album.channel)
        val logoIV = holder.albumLogoIV
        Glide.with(context).load(album.logo_album)
            .into(holder.albumLogoIV)


        holder.itemView.setOnClickListener{
            (context as AppCompatActivity).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, NgheNgayFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }
}