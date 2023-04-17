package com.example.channel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView

class ListTapFragment : Fragment() {
    private lateinit var ivBack4: ImageView

    private lateinit var listView: ListView
    private lateinit var listTapAdapter:ListTapAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_tap, container, false)
        ivBack4 = view.findViewById(R.id.ivBack4)
        ivBack4?.setOnClickListener {
            parentFragmentManager.popBackStack()
            //nhớ phải add fragment này vào backStack thì mới dùng được
            //https://www.youtube.com/watch?v=b9a3-gZ9CGc

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = mutableListOf<ListTapData>()
        list.add(ListTapData("23 thang 3","Podcast 1","20 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))
        list.add(ListTapData("23 thang 4","Podcast 2","25 phut"))



        listTapAdapter = ListTapAdapter(requireContext(), R.layout.list_tap, list)
        listView = view.findViewById(R.id.lvTap)

        listView.adapter = listTapAdapter


    }
}