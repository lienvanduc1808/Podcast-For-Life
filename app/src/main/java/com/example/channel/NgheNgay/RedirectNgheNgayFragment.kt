package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RedirectNgheNgayFragment : Fragment()  {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nghe_ngay, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("send_idAlbum1", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@RedirectNgheNgayFragment)
            val idAlbum = result.getString("idAlbum").toString()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, NgheNgayFragment()).addToBackStack(null).commit()

            val send_data = Bundle().apply {
                putString("idAlbum", idAlbum)
                Log.d("idAlbum", idAlbum)

            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)
        }
    }

}