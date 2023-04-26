package com.example.channel.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RelativeLayout
import com.example.channel.R

class NotiFragment : Fragment() {
    private lateinit var rlBack6: RelativeLayout
    private lateinit var lvNoti: ListView
    private lateinit var notiAdapter: NotiAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_noti, container, false)
        rlBack6 = view.findViewById(R.id.rlBack6)
        rlBack6.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lvNoti = view.findViewById(R.id.lvNoti)
        val items = listOf(
            NotiData(R.drawable.trikycamxuc, "#25 - người lớn cô đơn da phat hanh mot tap moi da phat hanh mot tap moida phat hanh mot tap moi ", "2 gio truoc"),
            NotiData(R.drawable.trikycamxuc,"16 THÁNG 3 da phat hanh mot tap moi da phat hanh mot tap moi da phat hanh mot tap moi", "2 gio truoc"),
            NotiData(R.drawable.trikycamxuc,"16 THÁNG người lớn cô đơnda phat hanh mot tap moida phat hanh mot tap moida phat hanh mot tap moida phat hanh mot tap moi", "2 gio truoc"),
            NotiData(R.drawable.trikycamxuc,"16 Tlớn cô đơnda phat hanh mot tap moida phat hanh mot tap moida phat hanh mot tap moida phat hanh mot tap moi", "2 gio truoc"),
        )
        notiAdapter = NotiAdapter(requireContext(), R.layout.item_noti, items)
        lvNoti.adapter = notiAdapter

    }

    override fun onPause() {
        super.onPause()
    }

}