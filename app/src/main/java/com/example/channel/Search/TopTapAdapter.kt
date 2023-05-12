package com.example.channel.Search


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.channel.R
import com.google.firebase.storage.FirebaseStorage


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

        val storageRef = FirebaseStorage.getInstance().reference
        val logo = currentItem?.img_top_tap

        val imageRef = storageRef.child("Album/$logo")

        // Get the download URL of the image
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            // Use the URL to display the image
            Glide.with(context).load(uri).placeholder(R.drawable.img_17).into(img_top_tap!!)
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e("FirebaseStorage", "Error getting download URL", exception)
        }



        return rowView!!
    }
}