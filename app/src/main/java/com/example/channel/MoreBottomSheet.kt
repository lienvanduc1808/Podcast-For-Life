package com.example.channel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoreBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */


class MoreBottomSheet : BottomSheetDialogFragment(){
    lateinit var ivCoverImage2: ImageView
    lateinit var tvTitle2: TextView
    lateinit var tvDate2: TextView
    lateinit var lvShareBtn: ListView
    lateinit var lvMore: ListView
    lateinit var ibClose: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_more, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        var shareonly = mutableListOf<features>()
        shareonly.add(features("Chia se?", R.drawable.baseline_ios_share_24))

        var featuresList = mutableListOf<features>()
        featuresList.add(features("Đi tới chương trình", R.drawable.baseline_ios_share_24))
        featuresList.add(features("Lưu tập", R.drawable.baseline_turned_in_not_24))
        featuresList.add(features("Sao chép liên kết", R.drawable.baseline_copy_link))
        featuresList.add(features("Báo cáo lo ngại", R.drawable.baseline_report))

        lvShareBtn = view.findViewById(R.id.lvShareBtn)
        lvShareBtn.adapter = MoreAdapter(requireContext(), R.layout.item_more, shareonly)

        lvMore = view.findViewById(R.id.lvMore)
        lvMore.adapter = MoreAdapter(requireContext(), R.layout.item_more, featuresList)

        tvTitle2 = view.findViewById(R.id.tvTitle2)
        tvTitle2.setSingleLine()

        tvDate2 = view.findViewById(R.id.tvDate2)
        tvDate2.setSingleLine()

        ibClose = view.findViewById(R.id.ibClose)
        ibClose.setOnClickListener {
            dismiss()
        }

    }
}

















