package com.example.channel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private val carouselDataList: ArrayList<Review>) :
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
        val review: Review = carouselDataList[position]
        holder.tvComment.setText(review.comment)
        holder.tvDateCmt.setText(review.date)
        holder.tvUserCmt.setText(review.user)
        holder.rbRating.setRating(review.rating.toFloat())
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}
