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
//import com.example.channel.testData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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


            val database = FirebaseDatabase.getInstance()
            val ref = database.reference.child("categories")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (categorySnapshot in dataSnapshot.children) {
                        for (albumSnapshot in categorySnapshot.child("albums").children) {
                            for (episodeSnapshot in albumSnapshot.child("episodes").children) {
                                if(currentItem?._id.toString().equals(episodeSnapshot.key.toString())){


                                    val numListener = episodeSnapshot.child("listener").value.toString().toInt()

                                    val newNumListener = numListener +1

                                    var cateRef = categorySnapshot.key.toString()
                                    Log.d("cateRef",cateRef)
                                    var AlbumRef = albumSnapshot.key.toString()
                                    Log.d("AlbumRef",cateRef)
                                    var episoedeRef = albumSnapshot.child("episodes").ref
                                    val reafRef = "categories" +"/" +cateRef+"/albums/"+AlbumRef+"/episodes/"+ episodeSnapshot.key.toString()
                                    Log.d("RealRef",reafRef)
                                    val myRef = database.getReference(reafRef)
                                    val updates: MutableMap<String, Any> = HashMap()
                                    updates["listener"] = newNumListener.toString()

                                    myRef.updateChildren(updates)





                                }

                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            })

        }



        return itemView!!
    }
}