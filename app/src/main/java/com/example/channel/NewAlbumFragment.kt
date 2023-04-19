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

class NewAlbumFragment : Fragment() {

    private val PICK_IMAGE: Int = 3
    var uriData:String=""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pickImage(view)
        turnBack(view)



        val edtTenAlbum  = view.findViewById<EditText>(R.id.edtTenAlbum)
        val edtTenTap= view.findViewById<EditText>(R.id.edtTenTap)
        val edtThemmota = view.findViewById<EditText>(R.id.edtThemmota)
        val imgView = view.findViewById<ImageView>(R.id.imageView)

        val txtXong = view.findViewById<TextView>(R.id.txtXong)
        txtXong.setOnClickListener {

            val tenAlbum = edtTenAlbum.text.toString()
            val tenTap = edtTenTap.text.toString()
            val moTa = edtThemmota.text.toString()
            val result = Bundle().apply {
                putString("task_tenAlbum", tenAlbum)
                putString("task_tenTap", tenTap)
                putString("task_description", moTa)
                putString("task_uri", uriData)

            }
            parentFragmentManager.setFragmentResult("xong", result)
            parentFragmentManager.popBackStack()

        }
















    }
    private fun pickImage(view: View) {

        val btnImage = view.findViewById<Button>(R.id.btnImage)
        btnImage.setOnClickListener {
            val intent  = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent,"Title"),PICK_IMAGE)


        }
    }
    private fun turnBack(view: View) {
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            parentFragmentManager.popBackStack()


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val imgView = view?.findViewById<ImageView>(R.id.imageView)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val uri: Uri = data.data!!
                imgView?.setImageURI(uri)
                uriData = uri.toString()




            }


        }



    }
}