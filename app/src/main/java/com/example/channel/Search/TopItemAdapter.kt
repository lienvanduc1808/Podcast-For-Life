package com.example.channel.Search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.channel.R


class TopItemAdapter(private val carouselDataList: ArrayList<Top_Item>) :
    RecyclerView.Adapter<TopItemAdapter.CarouselItemViewHolder>() {
    var onItemClick: ((Top_Item) -> Unit)? = null

    inner class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val albumNameTV = view.findViewById<TextView>(R.id.tvAlbumName)
        val albumArtistTV = view.findViewById<TextView>(R.id.tvAlbumArtist)
        val albumLogoIV = view.findViewById<ImageView>(R.id.ivLogo)
        val rankingItem = view.findViewById<TextView>(R.id.txtRankingItem)
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
        logoIV.setImageResource(album.logo)
        val rankingItem = holder.rankingItem
        rankingItem.setText(album.rank)
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}
