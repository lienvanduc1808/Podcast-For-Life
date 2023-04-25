package com.example.channel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2

class NgheNgayFragment : Fragment() {
    private lateinit var ibBack: ImageButton

    private lateinit var tvAllEpisode: TextView
    private lateinit var lvListEpisode: ListView
    private lateinit var listOpisodeAdapter: ListOpisodeAdapter

    private lateinit var tvAllReview: TextView


    private lateinit var pb5start: ProgressBar
    private lateinit var pb4start: ProgressBar
    private lateinit var pb3start: ProgressBar
    private lateinit var pb2start: ProgressBar
    private lateinit var pb1start: ProgressBar

    private lateinit var vpReview: ViewPager2
//    private lateinit var reviewAdapter: ReviewAdapter

    private lateinit var tvMakeReview: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_nghe_ngay, container, false)
        ibBack = view.findViewById(R.id.ibBack)
        ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        tvAllEpisode = view.findViewById(R.id.tvAllEpisode)
        tvAllEpisode?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, ListTapFragment()).addToBackStack(null).commit()
        }

        tvAllReview = view.findViewById(R.id.tvAllReview)
        tvAllReview?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, AllReviewFragment()).addToBackStack(null).commit()
        }

        tvMakeReview = view.findViewById(R.id.tvMakeReview)
        tvMakeReview?.setOnClickListener {
            ReviewBottomSheet().show(getParentFragmentManager(), "Review screen")
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf(
        testData("16 THÁNG 3", "#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
        testData("16 THÁNG 3", "#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
        testData("16 THÁNG 3", "#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
        )

        listOpisodeAdapter = ListOpisodeAdapter(requireContext(), R.layout.list_opisode, items)
        lvListEpisode = view.findViewById(R.id.lvListEpisode)
//        listView.setOnTouchListener { _, _ -> true }
//        listView.setOnTouchListener(null)
        lvListEpisode.adapter = listOpisodeAdapter


        pb5start = view.findViewById(R.id.pb5start)
        pb5start.setProgress(45)
        pb5start.setMax(100)
        pb4start = view.findViewById(R.id.pb4start)
        pb4start.setProgress(4)
        pb4start.setMax(100)
        pb3start = view.findViewById(R.id.pb3start)
        pb3start.setProgress(0)
        pb3start.setMax(100)
        pb2start = view.findViewById(R.id.pb2start)
        pb2start.setProgress(12)
        pb2start.setMax(100)
        pb1start = view.findViewById(R.id.pb1start)
        pb1start.setProgress(29)
        pb1start.setMax(100)

        val exReview = arrayListOf(
            Review("Album 1", "user 1", 3, "ngonngonngonngonngon", "12/12/2012"),
            Review("Album 1", "user 1", 4, "ngonngononngon", "12/12/2012"),
            Review("Album 1", "user 1", 2, "ngngonngonngonon", "12/12/2012"),
            Review("Album 1", "user 1", 1, "ngngonngonon", "12/12/2012"),
            Review("Album 1", "user 1", 4, "ngngonngonon", "12/12/2012"),
            Review("Album 1", "user 1", 5, "ngngonngonngonngonon", "12/12/2012"),
            Review("Album 1", "user 1", 2, "ngngonngonon", "12/12/2012")
        )

//        reviewAdapter = ReviewAdapter(exReview)
        vpReview = view.findViewById(R.id.vpReview)
        vpReview.adapter = ReviewAdapter(exReview)

    }

    override fun onResume() {
        super.onResume()
    }

}