package com.example.channel

import android.content.Context
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

class HomeAdapter(private val carouselDataList: ArrayList<Album>, val context: Context) :
    RecyclerView.Adapter<HomeAdapter.CarouselItemViewHolder>() {
    var onItemClick: ((Album) -> Unit)? = null

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
       val album: Album = carouselDataList[position]
        val albumNameTV = holder.albumNameTV
        albumNameTV.setText(album.name)
        val albumArtistTV = holder.albumArtistTV
        albumArtistTV.setText(album.channel)
      //  val logoIV = holder.albumLogoIV
        //logoIV.setImageURI(album.logo.toUri())
        Glide.with(context).load(album.logo)
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
