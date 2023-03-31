package com.example.channel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
class NgheNgayFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var adapter: listOpisodeAdapter
    private lateinit var moreHoriz: ImageButton
    private var test = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nghe_ngay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf(
        testData("16 THÁNG 3", "#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
        testData("16 THÁNG 3", "#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
        testData("16 THÁNG 3", "#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
        )

        adapter = listOpisodeAdapter(requireContext(), R.layout.list_opisode, items)
        listView = view.findViewById(R.id.listView)
        listView.adapter = adapter

        moreHoriz = view.findViewById(R.id.moreHorizBtn)
        var popupWindow: PopupWindow? = null
        moreHoriz?.setOnClickListener {
            if(popupWindow == null){
//                popupWindow = PopupWindow(requireContext())
                val popupView = layoutInflater.inflate(R.layout.popup_more_horiz, null)
                popupWindow = PopupWindow(popupView,800, ViewGroup.LayoutParams.WRAP_CONTENT)
//                popupWindow?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.round_popup_morehoriz))

                popupWindow?.setOnDismissListener {
                    popupWindow = null
                }
            }
            if (popupWindow?.isShowing == true) {
                popupWindow?.dismiss()
            } else {
                popupWindow?.showAsDropDown(moreHoriz, 0, 30)
            }

        }
    }
}