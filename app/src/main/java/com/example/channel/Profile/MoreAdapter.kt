package com.example.channel.Profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.channel.R

class features(val name:String,val icon:Int)

class MoreAdapter(var mCtx: Context, var resources:Int, var items: List<features>): ArrayAdapter<features>(mCtx, resources, items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resources, null)
        val namefeatures: TextView = view.findViewById(R.id.tvNameFeature)
        val iconfeatures: ImageView = view.findViewById(R.id.ivIconFeature)

        var mItem = items[position]
        namefeatures.text = mItem.name
        iconfeatures.setImageDrawable(mCtx.resources.getDrawable(mItem.icon))
        return view
    }
}