package com.example.channel.Library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import com.example.channel.ListSavedAdapter
import com.example.channel.R
import com.example.channel.NgheNgay.episodeData

class SavedFragment : Fragment() {
    private lateinit var returnLibraryBtn: ImageButton
    private lateinit var listViewSaved: ListView
    private lateinit var listSavedAdapter: ListSavedAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)
        returnLibraryBtn = view.findViewById(R.id.ibReturnLibrary)
        returnLibraryBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewSaved = view.findViewById(R.id.lvSaved)
        val items = listOf(
            episodeData("https://firebasestorage.googleapis.com/v0/b/testdb-80aa6.appspot.com/o/Category%2Fmeoo.jpg?alt=media&token=f2f5206a-6c1e-49a0-8553-00a555f19d00","16 THÁNG 3", "#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
            episodeData("https://firebasestorage.googleapis.com/v0/b/testdb-80aa6.appspot.com/o/Category%2Fmeoo.jpg?alt=media&token=f2f5206a-6c1e-49a0-8553-00a555f19d00", "12 tháng 3","#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
            episodeData("https://firebasestorage.googleapis.com/v0/b/testdb-80aa6.appspot.com/o/Category%2Fmeoo.jpg?alt=media&token=f2f5206a-6c1e-49a0-8553-00a555f19d00", "15 tháng 2","#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
        )
        listSavedAdapter = ListSavedAdapter(requireContext(), R.layout.list_opisode, items)
        listViewSaved.adapter = listSavedAdapter

    }

    override fun onPause() {
        super.onPause()
        listSavedAdapter.dismissAllPopups()
    }

}