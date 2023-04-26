package com.example.channel.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.channel.R


class MyPodcastFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var adapter: MyPodcastAdapter

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


        list.add(MyPodCastData(R.drawable.img,"Xa hoi ","Lời khuyên hữu ích","mọi chuyện trong cuộc sống","Mỗi podcast của kênh The Present Writer có độ dài từ 15 đến 30 phút, chủ đề xoay quanh các bài học trong cuộc sống, các cách phát triển bản thân theo trường phái tối giản. Khi đối diện với thất bại, Bạn đang sống cho hiện tại, quá khứ hay tương lai?",""))
        list.add(MyPodCastData(R.drawable.img_9,"Xa hoi ","Marketing","lắng nghe và chia sẻ","Một hành trình của âu lo, Ngừng so sánh bản thân và ghen tỵ với người khác hay Bình ổn tâm lý trong mùa dịch, … đây đều là những vấn đề thực tiễn nhưng không kém phần thú vị mà Chi Nguyễn đưa vào trong mỗi tập podcast của mình.",""))
        list.add(MyPodCastData(R.drawable.img_8,"Xa hoi ","Bình thường một cách bất thường","Bên cạnh đó, những lời khuyên hữu ích dựa trên trải nghiệm của bản thân từ lúc học tập, sinh sống và làm việc tại Mỹ cũng đã được Chi Nguyễn chia sẻ thẳng thắn với mọi người. ","Đến thời điểm hiện tại, Vietcetera đã sở hữu nhiều kênh podcast truyền cảm hứng trên Spotify với các chủ đề khác nhau trong cuộc sống và đó đều là những vấn đề nhận được nhiều sự quan tâm từ công chúng.",""))
        list.add(MyPodCastData(R.drawable.img_7,"Xa hoi ","sức khỏe tâm lý","những bệnh lý thời hiện đại","Bằng một chất giọng truyền cảm, ngọt ngào và tươi vui, chủ nhân kênh podcast The Present Writer hứa hẹn sẽ mang đến cho bạn một nguồn năng lượng tích cực, dẫn dắt bạn tiếp cận với những góc nhìn chân thực trong cuộc sống và hơn hết là qua đó bạn có thể mang về cho mình những kiến thức mới mẻ.",""))




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