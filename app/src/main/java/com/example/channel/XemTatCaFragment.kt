package com.example.channel

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class XemTatCaFragment : Fragment() {
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

        val items = listOf(
            Album("Text 1.1", "Text 1.2", R.drawable.trikycamxuc),
            Album("Text 2.1", "Text 2.2", R.drawable.trikycamxuc),
            Album("Text 3.1", "Text 3.2", R.drawable.trikycamxuc),
            Album("Text 1.1", "Text 1.2", R.drawable.trikycamxuc),
            Album("Text 2.1", "Text 2.2", R.drawable.trikycamxuc),
            Album("Text 3.1", "Text 3.2", R.drawable.trikycamxuc),
            Album("Text 1.1", "Text 1.2", R.drawable.trikycamxuc),
            Album("Text 2.1", "Text 2.2", R.drawable.trikycamxuc),
            Album("Text 3.1", "Text 3.2", R.drawable.trikycamxuc),
            // add more items here
        )


        rvListAlbum.adapter = XemTatCaAdapter(items, requireContext())
        rvListAlbum.layoutManager = GridLayoutManager(context, 2)

        //val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)

//        recyclerView.apply {
//            layoutManager = GridLayoutManager(context, 2)
//            addItemDecoration(object : RecyclerView.ItemDecoration() {
//                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//                    outRect.bottom = spacingInPixels
//                }
//            })
//        }


    }
}