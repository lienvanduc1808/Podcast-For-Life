package com.example.channel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.channel.Search.DanhMuc
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DanhMucAdapter(private val data: ArrayList<DanhMuc>, val context: Context) : RecyclerView.Adapter<DanhMucAdapter.ViewHolder>() {
    var onItemClick: ((DanhMuc) -> Unit)? = null
    private lateinit var storageReference: StorageReference
    val database = FirebaseDatabase.getInstance()
    val ref = database.reference.child("categories")
    val storageRef = FirebaseStorage.getInstance().reference
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_danh_muc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:DanhMuc = data[position]
//        Glide.with(context).load(item.cate_image).placeholder(R.drawable.img_17)
//            .into(holder.imageView)
        //holder.imageView.setImageResource(item.image)
        val img = item.cate_image
        val imageRef = storageRef.child("Category/$img")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            // Use the URL to display the image
            Glide.with(context).load(uri).into(holder.imageView)
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e("FirebaseStorage", "Error getting download URL", exception)
        }
        holder.textView.text = item.cate_name

        holder.itemView.setOnClickListener {
            when (position) {
                0 -> {
                    onItemClick?.invoke(DanhMuc("New Fragment 1", ""))
                }
                1 -> {
                    onItemClick?.invoke(DanhMuc("Tin tức", ""))

                }
                2 -> {
                    onItemClick?.invoke(DanhMuc("Thể thao", ""))

                }
                3 -> {
                    onItemClick?.invoke(DanhMuc("Hài", ""))

                }
                4 -> {
                    onItemClick?.invoke(DanhMuc("Kinh doanh", ""))

                }
                5 -> {
                    onItemClick?.invoke(DanhMuc("Xã hội và văn hóa", ""))

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