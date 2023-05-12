package com.example.channel.admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.channel.R
import com.example.channel.Search.DanhMuc
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CateManageAdapter(private val data: ArrayList<DanhMuc>, val context: Context) : RecyclerView.Adapter<CateManageAdapter.ViewHolder>() {
    var onItemClick: ((DanhMuc) -> Unit)? = null
    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_item_danh_muc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: DanhMuc = data[position]

        holder.catName.text = item.cate_name
        holder.editTV.setOnClickListener{
            val fragment = AdminEditCateFragment()

            // Pass the selected category information to the fragment
            val bundle = Bundle()
            bundle.putString("cate_name", item.cate_name)
            bundle.putString("cate_image", item.cate_image)
            fragment.arguments = bundle

            replaceFragment(fragment)

        }
        val name = item.cate_name
        holder.deleteTV.setOnClickListener{
            val database = FirebaseDatabase.getInstance()
            val ref = database.reference.child("categories")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (categorySnapshot in dataSnapshot.children) {
                       val cate =  categorySnapshot.child("cate_name").value as String
                        if(name.equals(cate)){

                            var cateRef = categorySnapshot.key.toString()

                            val reafRef = "categories" +"/" +cateRef
                            Log.d("Realf",reafRef)
                            val myRef = database.getReference(reafRef)
                            myRef.removeValue()
                            Toast.makeText(context, "Danh mục $name đã được xóa", Toast.LENGTH_SHORT)
                        }

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

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
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = (context as FragmentActivity).supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.admin_frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }
}