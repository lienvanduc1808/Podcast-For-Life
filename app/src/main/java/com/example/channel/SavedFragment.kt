package com.example.channel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.constraintlayout.widget.ConstraintLayout

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
            episodeData(R.drawable.trikycamxuc,"16 THÁNG 3", "#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
            episodeData(R.drawable.trikycamxuc, "12 tháng 3","#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
            episodeData(R.drawable.trikycamxuc, "15 tháng 2","#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
        )
        listSavedAdapter = ListSavedAdapter(requireContext(), R.layout.list_opisode, items)
        listViewSaved.adapter = listSavedAdapter

    }

    override fun onPause() {
        super.onPause()
        listSavedAdapter.dismissAllPopups()
    }

}