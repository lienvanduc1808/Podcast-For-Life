package com.example.channel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import com.makeramen.roundedimageview.RoundedImageView

class EditMyPodcastFragment : Fragment() {





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_my_podcast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        turnBack(view)
        parentFragmentManager.setFragmentResultListener("send_item", this) { _, result ->

            parentFragmentManager.beginTransaction().show(this@EditMyPodcastFragment)
            val taskDanhmuc = result.getString("edt_danhmuc")
            val taskAlbumName = result.getString("edt_tenalbum")
            val taskTenTap = result.getString("edt_tentap")
            val taskDescription = result.getString("edt_mota")
            val taskUri = result.getString("edt_uri")
            val taskImg = result.getInt("edt_img")




            val txtNameUri = view.findViewById<TextView>(R.id.txtNameUri)
            val txtChonDanhmuc =  view.findViewById<TextView>(R.id.txtChonDanhmuc)
            val txtNewAlbumName =  view.findViewById<TextView>(R.id.txtNewAlbumName)
            val txtNewEsposideName =  view.findViewById<TextView>(R.id.txtNewEsposideName)
            val txtNewDes = view.findViewById<TextView>(R.id.txtNewDes)
            val newImage =   view.findViewById<RoundedImageView>(R.id.newImage)
            val spnAlbum =   view.findViewById<TextView>(R.id.txtTenAlbum)
            val items = mutableListOf<String>()
            items.add("-------------------------------------------------------------")
            items.add("Tạo Album mới")
            items.add("Thêm vào Album đã có")

            txtNameUri.setText(taskUri)
            txtChonDanhmuc.setText(taskDanhmuc)
            txtNewAlbumName.setText(taskAlbumName)
            txtNewEsposideName.setText(taskTenTap)
            txtNewDes.setText(taskDescription)
            newImage.setImageResource(taskImg)












        }









    }

    private fun turnBack(view: View) {
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            parentFragmentManager.popBackStack()


        }

    }


}