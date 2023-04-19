package com.example.channel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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


class ReviewAdapter4LV(context: Context, resource: Int, objects: List<Review>):
    ArrayAdapter<Review>(context, resource, objects) {
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
        tvUserCmt?.text = currentItem?.user

        val rbRating = itemView?.findViewById<RatingBar>(R.id.rbRating)
        currentItem?.rating?.let { rbRating?.setRating(it.toFloat()) }

        return itemView!!
    }

}