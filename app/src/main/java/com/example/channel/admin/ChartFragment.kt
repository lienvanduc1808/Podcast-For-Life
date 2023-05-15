package com.example.channel.admin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.db.williamchart.*

import com.example.channel.R
import com.example.channel.Search.DanhMuc
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference



class ChartFragment : Fragment() {
    companion object {


        private const val animationDuration = 1000L
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_chart, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lineChart =view.findViewById<com.db.williamchart.view.LineChartView>(R.id.lineChart)
        val tvChartData = view.findViewById<TextView>(R.id.tvChartData)
        val axis_x = view.findViewById<View>(R.id.x_axis)
        val axis_y = view.findViewById<View>(R.id.y_axis)

        val LineSet = mutableListOf(
            "0" to 0f
        )


        val database = FirebaseDatabase.getInstance()
        val ref = database.reference.child("categories")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (categorySnapshot in dataSnapshot.children) {
                    for (albumSnapshot in categorySnapshot.child("albums").children) {
                        for (episodeSnapshot in albumSnapshot.child("episodes").children) {

                                val numListener = episodeSnapshot.child("listener").value.toString().toInt()



                            val episodeName = episodeSnapshot.child("title").value.toString()

                            LineSet.add(episodeName to numListener.toFloat())








                        }
                    }
                }
                Log.d("lineset",LineSet.toString())

                lineChart.gradientFillColors =
                    intArrayOf(
                        Color.parseColor("#81FFFFFF"),
                        Color.TRANSPARENT
                    )
                lineChart.animation.duration = animationDuration
                lineChart.onDataPointTouchListener = { index, _, _ ->
                    tvChartData.text = "Listener of " + LineSet.toList()[index].first
                        .toString() + " : " +
                        LineSet.toList()[index]
                            .second
                            .toString()
                }
                lineChart.animate(LineSet)
            }



            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })







    }
    override fun onDestroy() {
        super.onDestroy()

    }

}