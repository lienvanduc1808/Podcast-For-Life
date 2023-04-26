package com.example.channel.Profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.channel.R

class ExistAlbumFragment : Fragment() {

    private val PICK_IMAGE: Int = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exist_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        pickImage(view)

        turnBack(view)




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


            }
        }
    }
}