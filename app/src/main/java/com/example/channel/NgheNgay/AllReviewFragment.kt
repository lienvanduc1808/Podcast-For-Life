package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.example.channel.R
import com.example.channel.Search.reviewData


class AllReviewFragment : Fragment() {

    lateinit var ibBack2: ImageButton
    lateinit var tvBack: TextView
    lateinit var tvMakeReview2: TextView
    lateinit var lvAllReview: ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_all_review, container, false)
        ibBack2 = view.findViewById(R.id.ibBack2)
        ibBack2?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        tvBack = view.findViewById(R.id.tvBack)
        tvBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        tvMakeReview2 = view.findViewById(R.id.tvMakeReview2)
        tvMakeReview2?.setOnClickListener {
            ReviewBottomSheet().show(getParentFragmentManager(), "Review screen")
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exReview = arrayListOf(
            reviewData("Album 1", "user 1", 3, "ngonngonngonngonngon", "12/12/2012"),
            reviewData("Album 1", "user 1", 4, "ngonngononngon", "12/12/2012"),
            reviewData("Album 1", "user 1", 2, "ngngonngonngonon", "12/12/2012"),
            reviewData("Album 1", "user 1", 1, "ngngonngonon", "12/12/2012"),
            reviewData("Album 1", "user 1", 4, "ngngonngonon", "12/12/2012"),
            reviewData("Album 1", "user 1", 5, "ngngonngonngonngonon", "12/12/2012"),
            reviewData("Album 1", "user 1", 2, "ngngonngonon", "12/12/2012")
        )

        //get by id
        lvAllReview = view.findViewById(R.id.lvAllReview)
        lvAllReview.adapter = ReviewAdapter4LV(requireContext(), R.layout.album_review, exReview)
    }


}