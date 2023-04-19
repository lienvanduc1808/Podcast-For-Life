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
            chonAlbum(view)

            txtNameUri.setText(taskUri)
            txtChonDanhmuc.setText(taskDanhmuc)
            txtNewAlbumName.setText(taskAlbumName)
            txtNewEsposideName.setText(taskTenTap)
            txtNewDes.setText(taskDescription)
            newImage.setImageResource(taskImg)












        }









    }
    private fun chonAlbum(view: View) {
        val items = mutableListOf<String>()
        items.add("-------------------------------------------------------------")
        items.add("Tạo Album mới")
        items.add("Thêm vào Album đã có")

        val adt = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,items)
        val spnAlbum  = view.findViewById<Spinner>(R.id.spnAlbum)
        spnAlbum.adapter = adt
        spnAlbum.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                val selectedFragment = p0?.getItemAtPosition(p2).toString()

                when (selectedFragment) {

                    "Tạo Album mới" -> {
                        val fragment = NewAlbumFragment()
                        parentFragmentManager.beginTransaction()
                            .add(R.id.frame_layout, fragment)

                            .addToBackStack(null)
                            .hide(this@EditMyPodcastFragment)
                            .commit()


                    }
                    "Thêm vào Album đã có" -> {
                        val fragment = ExistAlbumFragment()
                        parentFragmentManager.beginTransaction()
                            .add(R.id.frame_layout, fragment)

                            .addToBackStack(null)
                            .hide(this@EditMyPodcastFragment)
                            .commit()
                    }


                }





//
            }


            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

    private fun turnBack(view: View) {
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            parentFragmentManager.popBackStack()


        }

    }


}