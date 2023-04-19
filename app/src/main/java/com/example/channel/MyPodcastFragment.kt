package com.example.channel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.net.toUri
import com.makeramen.roundedimageview.RoundedImageView


class MyPodcastFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var adapter:MyPodcastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_podcast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = mutableListOf<MyPodCastData>()
        list.add(MyPodCastData(R.drawable.trikycamxuc,"Xa hoi ","fff dfbf fshfbf fhfhf","fwef fhgfwehj fwhjfweyfh fjwfguyfn fwhfyu","fwefw fe yceytf aydgweyfw yugwyefvw ufweycif ufgwefb ufgwefveyvf ydqyfgq duqdyufqd uqfdqwyfq duyqwyfdqw dygqwyfdqw dyvq",""))
        list.add(MyPodCastData(R.drawable.trikycamxuc,"Xa hoi ","fff dfbf fshfbf fhfhf","fwef fhgfwehj fwhjfweyfh fjwfguyfn fwhfyu","fwefw fe yceytf aydgweyfw yugwyefvw ufweycif ufgwefb ufgwefveyvf ydqyfgq duqdyufqd uqfdqwyfq duyqwyfdqw dygqwyfdqw dyvq",""))
        list.add(MyPodCastData(R.drawable.trikycamxuc,"Xa hoi ","fff dfbf fshfbf fhfhf","fwef fhgfwehj fwhjfweyfh fjwfguyfn fwhfyu","fwefw fe yceytf aydgweyfw yugwyefvw ufweycif ufgwefb ufgwefveyvf ydqyfgq duqdyufqd uqfdqwyfq duyqwyfdqw dygqwyfdqw dyvq",""))
        list.add(MyPodCastData(R.drawable.trikycamxuc,"Xa hoi ","fff dfbf fshfbf fhfhf","fwef fhgfwehj fwhjfweyfh fjwfguyfn fwhfyu","fwefw fe yceytf aydgweyfw yugwyefvw ufweycif ufgwefb ufgwefveyvf ydqyfgq duqdyufqd uqfdqwyfq duyqwyfdqw dygqwyfdqw dyvq",""))




        adapter = MyPodcastAdapter(requireContext(), R.layout.my_podcast_item, list)
        listView = view.findViewById(R.id.lvMyPodcast)

        listView.adapter = adapter

        parentFragmentManager.setFragmentResultListener("xong", this) { _, result ->

            parentFragmentManager.beginTransaction().show(this@MyPodcastFragment)
//            val taskAlbumName = result.getString("task_tenAlbum")
//            val taskTenTap = result.getString("task_tenTap")
//            val taskDescription = result.getString("task_description")
//            val taskUri = result.getString("task_uri")
//            val taskDanhMuc = result.getString("task_danhmuc")
//            val taskAudio = result.getString("task_audio")
//
//            list.add(MyPodCastData(taskUri!!.toInt(),taskDanhMuc.toString(),taskAlbumName.toString(),taskTenTap.toString(),taskDescription.toString(),taskAudio.toString()))




        }


        val listener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val fragment = EditMyPodcastFragment()
                parentFragmentManager.beginTransaction()
                    .add(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .hide(this@MyPodcastFragment)
                    .commit()
                val send_data = Bundle().apply {
                    putString("edt_danhmuc",list[position].danh_muc )
                    putString("edt_tenalbum",list[position].ten_album )
                    putString("edt_tentap",list[position].ten_tap)
                    putString("edt_mota",list[position].mo_ta )
                    putString("edt_uri",list[position].uri_podcast )
                    putInt("edt_img",list[position].img_podcast )
                }
                parentFragmentManager.setFragmentResult("send_item", send_data)


            }
        }
        listView.setOnItemClickListener(listener)

        //quay lại
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
                if(fragmentManager!=null){
                    fragmentManager?.popBackStack()

                }
            //nhớ phải add fragment này vào backStack thì mới dùng được
            //https://www.youtube.com/watch?v=b9a3-gZ9CGc

        }

        val txtTaoMoi = view.findViewById<TextView>(R.id.txtTaoMoi)
        txtTaoMoi.setOnClickListener {
            val fragment = AddNewPodcastFragment()
            parentFragmentManager.beginTransaction()
                .add(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .hide(this@MyPodcastFragment)
                .commit()
        }


    }




}