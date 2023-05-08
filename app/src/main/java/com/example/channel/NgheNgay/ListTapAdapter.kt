package com.example.channel.NgheNgay


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.channel.R


class ListTapAdapter(context: Context, resource: Int, list: List<episodeData>):
    ArrayAdapter<episodeData>(context, resource, list) {

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
            EpisodeBottomSheet().show((context as AppCompatActivity).getSupportFragmentManager(), "Episode screen")

            val send_data = Bundle().apply {
                putString("idAlbum", currentItem?.img.toString())
                putString("position", position.toString())
            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idEpisode", send_data)

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


        ngayThang?.text = currentItem?.date
        tenTap?.text = currentItem?.title
//        phut?.text = currentItem?.phut
        phut?.text = "20s"



        return rowView!!


    }


}