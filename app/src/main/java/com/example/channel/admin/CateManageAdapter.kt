package com.example.channel.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.channel.DanhMuc
import com.example.channel.R

class CateManageAdapter(private val data: ArrayList<DanhMuc>, val context: Context) : RecyclerView.Adapter<CateManageAdapter.ViewHolder>() {
    var onItemClick: ((DanhMuc) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_item_danh_muc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: DanhMuc = data[position]

        holder.textView.text = item.name

        holder.itemView.setOnClickListener {

            onItemClick?.invoke(DanhMuc("New Fragment 2", ""))


        }

    }

    override fun getItemCount() = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(R.id.adminCateNameTV)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])

            }
        }
    }
}