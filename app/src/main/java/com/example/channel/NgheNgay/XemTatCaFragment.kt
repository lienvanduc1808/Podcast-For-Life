package com.example.channel.NgheNgay



import android.annotation.SuppressLint
import com.example.channel.R
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

class XemTatCaFragment(private val albumList: ArrayList<albumData>) : Fragment() {
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

        rvListAlbum.adapter = XemTatCaAdapter(albumList, requireContext())
        rvListAlbum.layoutManager = GridLayoutManager(context, 2)
    }
}




//
//old code
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.channel.R
//
//class XemTatCaFragment : Fragment() {
//    private lateinit var ivBack3: ImageView
//
//    private lateinit var rvListAlbum: RecyclerView
//    @SuppressLint("MissingInflatedId")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_xem_tat_ca, container, false)
//        ivBack3 = view.findViewById(R.id.ivBack3)
//        ivBack3?.setOnClickListener {
//            parentFragmentManager.popBackStack()
//        }
//        return view
//    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        rvListAlbum = view.findViewById(R.id.rvListAlbum)
//
//        val items = listOf(
//            albumData("Lời khuyên hữu ích", "The Present Writer", R.drawable.img_9),
//            albumData("Marketing", "Vietcetera", R.drawable.img_8),
//            albumData("Tay mơ học đời bằng Tâm lý học", "Amateur Psychology", R.drawable.img_7 ),
//            albumData("Bình thường một cách bất thường ", "Oddly Normal", R.drawable.img_6),
//            albumData("sức khỏe tâm lý.", "Tâm Lý Học Tuổi Trẻ", R.drawable.img_5),
//            albumData(" mọi chuyện trong cuộc sống", "Spiderum", R.drawable.img_4 ),
//            albumData("câu chuyện lịch sử", "Bí Ẩn Sử Việt", R.drawable.img_3 ),
//            albumData("lắng nghe và chia sẻ ", "Radio Người Giữ Kỉ Niệm", R.drawable.img_2),
//            albumData("Vipassana", "Minh Niệm", R.drawable.img_1),
//            albumData("những bệnh lý thời hiện đạ", "Optimal Health Daily", R.drawable.img )
//            // add more items here
//        )
//
//
//        rvListAlbum.adapter = XemTatCaAdapter(items, requireContext())
//        rvListAlbum.layoutManager = GridLayoutManager(context, 2)
//
//        //val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
//
////        recyclerView.apply {
////            layoutManager = GridLayoutManager(context, 2)
////            addItemDecoration(object : RecyclerView.ItemDecoration() {
////                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
////                    outRect.bottom = spacingInPixels
////                }
////            })
////        }
//
//
//    }
//}