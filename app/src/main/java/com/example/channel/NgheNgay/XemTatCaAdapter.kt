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

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text1: TextView = itemView.findViewById(R.id.tvAlbumName)
    val text2: TextView = itemView.findViewById(R.id.tvAlbumArtist)
    val image: ImageView = itemView.findViewById(R.id.ivLogo)

}

class XemTatCaAdapter(private val items: List<albumData>, val context: Context) :
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
        Glide.with(context).load(item.logo_album)
            .into(holder.image)


        holder.itemView.setOnClickListener{
            (context as AppCompatActivity).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, NgheNgayFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount() = items.size

}