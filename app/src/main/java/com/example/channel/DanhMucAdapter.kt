package com.example.channel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DanhMucAdapter(private val data: ArrayList<DanhMuc>,val context: Context) : RecyclerView.Adapter<DanhMucAdapter.ViewHolder>() {
    var onItemClick: ((DanhMuc) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_danh_muc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:DanhMuc = data[position]
        Glide.with(context).load(item.image)
            .into(holder.imageView)
        //holder.imageView.setImageResource(item.image)
        holder.textView.text = item.name

        holder.itemView.setOnClickListener {
            when (position) {
                0 -> {
                    onItemClick?.invoke(DanhMuc("New Fragment 1", ""))
                }
                else -> {
                    onItemClick?.invoke(DanhMuc("New Fragment 2", ""))
                }
            }
        }

    }

    override fun getItemCount() = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivDanhMuc)
        val textView: TextView = itemView.findViewById(R.id.tvTenDanhMuc)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])

            }
        }
    }
}