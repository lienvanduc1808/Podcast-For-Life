package com.example.channel.NgheNgay


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import com.example.channel.R


class ListTapAdapter(context: Context, resource: Int,  list: List<ListTapData>):
    ArrayAdapter<ListTapData>(context, resource, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        if (rowView == null) {
            rowView = LayoutInflater.from(context).inflate(R.layout.list_tap, parent, false)
        }

        val currentItem = getItem(position)

        val ngayThang = rowView?.findViewById<TextView>(R.id.txtThoiGian)
        val tenTap = rowView?.findViewById<TextView>(R.id.txtTenTap)
        val phut = rowView?.findViewById<TextView>(R.id.txtPhut)
        val imgBtnPlay = rowView?.findViewById<ImageButton>(R.id.imgBtnPlay)
        val imgbtnMore = rowView?.findViewById<ImageButton>(R.id.imgBtnMore)
        imgBtnPlay?.setOnClickListener {

        }

        //popup menu cho imgbutton more
        val popupMenu = PopupMenu(rowView?.context, imgbtnMore)

        popupMenu.inflate(R.menu.menu_popup)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
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


        ngayThang?.text = currentItem?.ngay_thang
        tenTap?.text = currentItem?.ten_tap
        phut?.text = currentItem?.phut



        return rowView!!


    }


}