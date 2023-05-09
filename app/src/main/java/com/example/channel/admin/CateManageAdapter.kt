package com.example.channel.admin

import android.content.Context
import android.os.Bundle
import android.util.Log
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

        holder.catName.text = item.name
        holder.editTV.setOnClickListener{
            Log.d("ffb", "edit")
        }
        holder.itemView.setOnClickListener {

            //onItemClick?.invoke(DanhMuc(item.name, ""))
            when (position) {
                0 -> {
                    onItemClick?.invoke(DanhMuc("Tin tức", ""))

                }
                1 -> {
                    onItemClick?.invoke(DanhMuc("Thể thao", ""))

                }
                2 -> {
                    onItemClick?.invoke(DanhMuc("Hài", ""))

                }
                3 -> {
                    onItemClick?.invoke(DanhMuc("Kinh doanh", ""))

                }
                4 -> {
                    onItemClick?.invoke(DanhMuc("Xã hội và văn hóa", ""))

                }

            }

        }

    }

    override fun getItemCount() = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val catName: TextView = itemView.findViewById(R.id.adminCateNameTV)
        val editTV: TextView = itemView.findViewById(R.id.cateEdit)
        val deleteTV: TextView = itemView.findViewById(R.id.cateDelete)
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])

            }
        }
    }


}