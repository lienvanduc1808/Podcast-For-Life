package com.example.channel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class listOpisodeAdapter(context: Context, resource: Int, objects: List<testData>):
    ArrayAdapter<testData>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_opisode, parent, false)
        }

        val currentItem = getItem(position)

        val itemDate = itemView?.findViewById<TextView>(R.id.dateUpload)
        itemDate?.text = currentItem?.date

        val itemName = itemView?.findViewById<TextView>(R.id.nameOpisode)
        itemName?.text = currentItem?.opisode

        val itemDescript = itemView?.findViewById<TextView>(R.id.descriptOpisode)
        itemDescript?.text = currentItem?.descript

        return itemView!!
    }

}