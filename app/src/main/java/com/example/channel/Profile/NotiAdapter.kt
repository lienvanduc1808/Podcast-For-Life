package com.example.channel.Profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.channel.R


class NotiAdapter(context: Context, resource: Int, objects: List<NotiData>):
    ArrayAdapter<NotiData>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var notiView = convertView
        if (notiView == null) {
            notiView = LayoutInflater.from(context).inflate(R.layout.item_noti, parent, false)
        }

        val currentItem = getItem(position)

        val avatar3 = notiView?.findViewById<ImageView>(R.id.avatar3)
        currentItem?.avatar?.let { avatar3?.setImageResource(it) }

        val tvContent3 = notiView?.findViewById<TextView>(R.id.tvContent3)
        tvContent3?.text = currentItem?.content

        val tvTime3 = notiView?.findViewById<TextView>(R.id.tvTime3)
        tvTime3?.text = currentItem?.time

        return notiView!!
    }
}