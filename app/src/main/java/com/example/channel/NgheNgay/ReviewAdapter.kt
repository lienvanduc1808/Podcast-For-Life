package com.example.channel.NgheNgay

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.channel.R
import com.example.channel.NgheNgay.reviewData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ReviewAdapter(private val carouselDataList: ArrayList<reviewData>) :
    RecyclerView.Adapter<ReviewAdapter.CarouselItemViewHolder>() {


    inner class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvComment = view.findViewById<TextView>(R.id.tvComment)
        val tvDateCmt = view.findViewById<TextView>(R.id.tvDateCmt)
        val tvUserCmt = view.findViewById<TextView>(R.id.tvUserCmt)
        val rbRating = view.findViewById<RatingBar>(R.id.rbRating)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.album_review, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val review: reviewData = carouselDataList[position]
        holder.tvComment.setText(review.comment)
        holder.tvDateCmt.setText(review.date)
        val userReference = Firebase.database.getReference("users")
        userReference.child(review.from).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                holder.tvUserCmt.setText(dataSnapshot.child("name").value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("eror", "Failed to read value.", error.toException())
            }
        })
//        holder.tvUserCmt.setText(review.from)
        holder.rbRating.setRating(review.rating.toFloat())
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}


class ReviewAdapter4LV(context: Context, resource: Int, objects: List<reviewData>):
    ArrayAdapter<reviewData>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.album_review, parent, false)
        }

        val currentItem = getItem(position)

        val tvComment = itemView?.findViewById<TextView>(R.id.tvComment)
        tvComment?.text = currentItem?.comment

        val tvDateCmt = itemView?.findViewById<TextView>(R.id.tvDateCmt)
        tvDateCmt?.text = currentItem?.date

        val tvUserCmt = itemView?.findViewById<TextView>(R.id.tvUserCmt)
//        tvUserCmt?.text = currentItem?.from
        val userReference = Firebase.database.getReference("users")
        if (currentItem != null) {
            userReference.child(currentItem.from).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    tvUserCmt?.text = dataSnapshot.child("name").value.toString()
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.d("eror", "Failed to read value.", error.toException())
                }

            })
        }

        val rbRating = itemView?.findViewById<RatingBar>(R.id.rbRating)
        currentItem?.rating?.let { rbRating?.setRating(it.toFloat()) }

        return itemView!!
    }

}