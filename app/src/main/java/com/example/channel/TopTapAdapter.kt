package com.example.channel


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView


class TopTapAdapter(context: Context, resource: Int, list: List<TopTapData>):
    ArrayAdapter<TopTapData>(context, resource, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.top_tap, parent, false)
        }

        val currentItem = getItem(position)

        val time_top_tap = rowView?.findViewById<TextView>(R.id.txtTimeRankingTap)
        val ten_top_tap = rowView?.findViewById<TextView>(R.id.txtNameRankingTap)
        val rank_top_tap = rowView?.findViewById<TextView>(R.id.txtRankingTap)
        val img_top_tap = rowView?.findViewById<ImageView>(R.id.imgTopTap)
        val imgbtnMore = rowView?.findViewById<ImageButton>(R.id.imgBtnMoreRankingTap)

        //popup menu cho imgbutton more
        val popupMenu = PopupMenu(rowView?.context, imgbtnMore)

        popupMenu.inflate(R.menu.ranking_tap)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuPlay->{
                    true
                }
                R.id.menuSave -> {


                    true
                }
                R.id.menuDownload -> {

                    true
                }
                R.id.menuShare -> {

                    true
                }
                else -> false
            }
        }

        imgbtnMore?.setOnClickListener {

            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)

            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                popupMenu.show()
            }
            true

        }


        time_top_tap?.text = currentItem?.time_top_tap
        ten_top_tap?.text = currentItem?.ten_top_tap
        rank_top_tap?.text = currentItem?.rank_top_tap
        img_top_tap?.setImageResource(currentItem?.img_top_tap!!)





        return rowView!!


    }


}