package com.example.channel.Search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.channel.R

class AlbumAdapter(private var albums: List<Album>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MyViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(album: Album)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_album_item, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    override fun getItemCount() = albums.size

    fun setItems(albums: List<Album>) {
        this.albums = albums
        notifyDataSetChanged()
    }

}

class MyViewHolder(itemView: View, private val listener: AlbumAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val titleTextView: TextView = itemView.findViewById(R.id.search_text)
    private lateinit var album: Album
    init {
        itemView.setOnClickListener(this)
    }
    fun bind(album: Album) {
        this.album = album
        titleTextView.text = album.name
    }
    override fun onClick(v: View?) {
        listener.onItemClick(album)
    }
}
