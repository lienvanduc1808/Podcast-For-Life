package com.example.channel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text1: TextView = itemView.findViewById(R.id.tvAlbumName)
    val text2: TextView = itemView.findViewById(R.id.tvAlbumArtist)
    val image: ImageView = itemView.findViewById(R.id.ivLogo)

}
class XemTatCaAdapter(private val items: List<Album>) :
    RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.text1.text = item.name
        holder.text2.text = item.channel
        holder.image.setImageResource(item.logo)
    }

    override fun getItemCount() = items.size
}