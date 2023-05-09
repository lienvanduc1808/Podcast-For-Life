package com.example.channel.NgheNgay

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.channel.R
import com.example.channel.testData

class ListOpisodeAdapter(context: Context, resource: Int, objects: List<ListTapData>):
    ArrayAdapter<ListTapData>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_opisode, parent, false)
        }

        val currentItem = getItem(position)

        val itemName = itemView?.findViewById<TextView>(R.id.nameOpisode)
        itemName?.text = currentItem?.title

        val itemDescript = itemView?.findViewById<TextView>(R.id.descriptOpisode)
        itemDescript?.text = currentItem?.descript

        val itemDate = itemView?.findViewById<TextView>(R.id.dateUpload)
        itemDate?.text = currentItem?.date

        val playBtn = itemView?.findViewById<ImageButton>(R.id.ibPlay)
        playBtn?.setOnClickListener{
            EpisodeBottomSheet().show((context as AppCompatActivity).getSupportFragmentManager(), "Episode screen")

            val send_data = Bundle().apply {
                putString("idEpisode", currentItem?._id.toString())
                putString("dateEpisode", currentItem?.date.toString())
                putString("titleEpisode", currentItem?.title.toString())
                putString("descriptEpisode", currentItem?.descript.toString())
                putString("imgEpisode", currentItem?.img.toString())
                Log.d("idEpisode", currentItem?._id.toString())
//                putString("position", position.toString())
            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idEpisode", send_data)
        }

        return itemView!!
    }
}