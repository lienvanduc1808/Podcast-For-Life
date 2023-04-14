package com.example.channel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.makeramen.roundedimageview.RoundedImageView

class listSavedAdapter(context: Context, resource: Int, objects: List<episodeData>):
    ArrayAdapter<episodeData>(context, resource, objects) {
    private val popupWindows = mutableMapOf<Int, PopupWindow>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_episode_saved, parent, false)
        }

        var popupWindow: PopupWindow? = null
        val currentItem = getItem(position)

        val itemImg = itemView?.findViewById<RoundedImageView>(R.id.rivCover)
        itemImg?.setBackgroundResource(currentItem!!.img)

        val itemDate = itemView?.findViewById<TextView>(R.id.dateUpload)
        itemDate?.text = currentItem?.date

        val itemName = itemView?.findViewById<TextView>(R.id.nameEpisode)
        itemName?.text = currentItem?.title

        val itemDescript = itemView?.findViewById<TextView>(R.id.descriptOpisode)
        itemDescript?.text = currentItem?.descript

        //Popup
        val itemButtonMoreHoriz = itemView?.findViewById<ImageButton>(R.id.ibMoreHoriz)
        itemButtonMoreHoriz?.setOnClickListener{
                if(popupWindow == null){
                    val popupView = LayoutInflater.from(context).inflate(R.layout.popup_more_horiz_saved, null)
                    popupWindow = PopupWindow(popupView,800, ViewGroup.LayoutParams.WRAP_CONTENT)

                    popupWindow?.setOnDismissListener {
                        popupWindow = null
                    }
                    popupWindows[position] = popupWindow!!
                }
                if (popupWindows[position]?.isShowing == true) {
                    popupWindows[position]?.dismiss()
                } else {
                    popupWindows[position]?.showAsDropDown(itemButtonMoreHoriz, 0, 30)
                }

        }

        //Pop
        val itemButtonPlay = itemView?.findViewById<ImageButton>(R.id.ibPlayEpisode)
        itemButtonPlay?.setOnClickListener{
            //Mở tập podcast và đổi podcast thu nhỏ hiện tại
        }

        return itemView!!
    }

    fun dismissAllPopups() {
        for (popupWindow in popupWindows.values) {
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
            }
        }
    }
}