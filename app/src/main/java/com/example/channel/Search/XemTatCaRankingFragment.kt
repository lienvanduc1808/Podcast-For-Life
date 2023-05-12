package com.example.channel.Search

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.channel.R

class XemTatCaRankingFragment(private val albumList: ArrayList<Top_Item>) : Fragment() {
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
            parentFragmentManager.popBackStack()
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvListAlbum = view.findViewById(R.id.rvListAlbum)

        rvListAlbum.adapter = XemTatCaTopItemAdapter(albumList, requireContext())
        rvListAlbum.layoutManager = GridLayoutManager(context, 2)
    }
}