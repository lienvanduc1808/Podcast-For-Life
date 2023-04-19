package com.example.channel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


//class DanhMucAdapter(private val items: List<DanhMuc>) :
//    RecyclerView.Adapter<ItemViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_danh_muc, parent, false)
//
//        return ItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val item = items[position]
//        holder.text1.text = item.name
//        holder.image.setImageResource(item.image)
//    }
//
//    override fun getItemCount() = items.size
//}
class DanhMucAdapter(private val data: ArrayList<DanhMuc>) : RecyclerView.Adapter<DanhMucAdapter.ViewHolder>() {
    var onItemClick: ((DanhMuc) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_danh_muc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:DanhMuc = data[position]
        holder.imageView.setImageResource(item.image)
        holder.textView.text = item.name
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