package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import com.example.channel.R

class ListTapFragment : Fragment() {
    private lateinit var ivBack4: ImageView

    private lateinit var listView: ListView
    private lateinit var listTapAdapter: ListTapAdapter

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

        list.add(ListTapData("18 thang 5","Lời khuyên hữu ích","20 phut"))
        list.add(ListTapData("11 thang 4","Marketing","25 phut"))
        list.add(ListTapData("23 thang 4","Tay mơ học đời bằng Tâm lý học","25 phut"))
        list.add(ListTapData("08 thang 2","Bình thường một cách bất thường","25 phut"))
        list.add(ListTapData("24 thang 1","sức khỏe tâm lý","25 phut"))
        list.add(ListTapData("31 thang 1","mọi chuyện trong cuộc sống","25 phut"))
        list.add(ListTapData("20 thang 3","câu chuyện lịch sử","25 phut"))
        list.add(ListTapData("12 thang 5","lắng nghe và chia sẻ ","25 phut"))
        list.add(ListTapData("13 thang 6","Vipassana","25 phut"))
        list.add(ListTapData("23 thang 2","những bệnh lý thời hiện đại","25 phut"))




        listTapAdapter = ListTapAdapter(requireContext(), R.layout.list_tap, list)
        listView = view.findViewById(R.id.lvTap)

        listView.adapter = listTapAdapter


    }
}