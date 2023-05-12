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
import com.example.channel.Search.DanhMuc
import com.example.channel.R

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class UserManageAdapter(private val data: ArrayList<User>, val context: Context) : RecyclerView.Adapter<UserManageAdapter.ViewHolder>() {
    var onItemClick: ((User) -> Unit)? = null
    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: User = data[position]

        holder.userName.text = item.name
        holder.userEmail.text = item.email

        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("User/${item.idUser}")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            // Use the URL to display the image
            Glide.with(context).load(uri).placeholder(R.drawable.avt).into(holder.userImg)
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e("FirebaseStorage", "Error getting download URL", exception)
        }
        val mail = item.email
        holder.deleteTV.setOnClickListener{
            val database = FirebaseDatabase.getInstance()
            val ref = database.reference.child("users")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (userSnapshot in dataSnapshot.children) {

                        val email =  userSnapshot.child("email").value as String
                        if(mail.equals(email)){

                            var cateRef = userSnapshot.key.toString()

                            val reafRef = "users" +"/" +cateRef
                            Log.d("Realf",reafRef)
                            val myRef = database.getReference(reafRef)
                            myRef.removeValue()
                            Toast.makeText(context, "User $mail đã được xóa", Toast.LENGTH_SHORT)
                        }

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userName: TextView = itemView.findViewById(R.id.userNameTV)
        val userEmail: TextView = itemView.findViewById(R.id.emailUserTV)
        val userImg:ImageView = itemView.findViewById(R.id.user_image)
        val deleteTV: TextView = itemView.findViewById(R.id.userDelete)
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