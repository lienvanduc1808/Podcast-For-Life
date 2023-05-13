package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.channel.R
import com.example.channel.Search.Album

class XemTatCaFragment(private val albumList: ArrayList<Album>) : Fragment() {
    private lateinit var ivBack3: ImageView

    private lateinit var rvListAlbum: RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_xem_tat_ca, container, false)
        ivBack3 = view.findViewById(R.id.ivBack3)
        ivBack3?.setOnClickListener {
            parentFragmentManager.setFragmentResultListener("send_tendm", this) { _, result ->

                parentFragmentManager.beginTransaction().show(this@XemTatCaFragment)
                val taskDanhmuc = result.getString("tendanhmucs")

                val send_data = Bundle().apply {
                    putString("tendanhmuc", taskDanhmuc)
                }
                (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_dm", send_data)

            }

            parentFragmentManager.popBackStack()
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvListAlbum = view.findViewById(R.id.rvListAlbum)

        rvListAlbum.adapter = XemTatCaAdapter(albumList, requireContext())
        rvListAlbum.layoutManager = GridLayoutManager(context, 2)
    }
}