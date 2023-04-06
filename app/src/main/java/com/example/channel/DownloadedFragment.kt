package com.example.channel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DownloadedFragment : Fragment() {
    private lateinit var ibReturnLibrary: ImageButton
    private lateinit var rvListAlbumDownloaded: RecyclerView
    private lateinit var ibMoreHoriz: ImageButton

    private var popupWindow: PopupWindow? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_downloaded, container, false)

        val view = inflater.inflate(R.layout.fragment_downloaded, container, false)
        rvListAlbumDownloaded = view.findViewById(R.id.rvListAlbumDownloaded)

        val items = listOf(
            Album("Tri ki cam xuc", "10 tap", R.drawable.trikycamxuc),
            Album("Tri ki cam xuc", "10 tap", R.drawable.trikycamxuc),
            Album("Tri ki cam xuc", "10 tap", R.drawable.trikycamxuc),
            Album("Tri ki cam xuc", "10 tap", R.drawable.trikycamxuc),
            Album("Tri ki cam xuc", "10 tap", R.drawable.trikycamxuc),
            Album("Tri ki cam xuc", "10 tap", R.drawable.trikycamxuc),
            Album("Tri ki cam xuc", "10 tap", R.drawable.trikycamxuc),
        )

        rvListAlbumDownloaded.adapter = XemTatCaAdapter(items, requireContext())
        rvListAlbumDownloaded.layoutManager = GridLayoutManager(context, 2)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ibReturnLibrary = view.findViewById(R.id.ibReturnLibrary)
        ibReturnLibrary.setOnClickListener {
            parentFragmentManager.popBackStack()
        }



        ibMoreHoriz = view.findViewById(R.id.ibMoreHoriz)
        ibMoreHoriz?.setOnClickListener {
            if(popupWindow == null){
                val popupView = LayoutInflater.from(context).inflate(R.layout.popup_menu_downloaded, null)
                popupWindow = PopupWindow(popupView,900, ViewGroup.LayoutParams.WRAP_CONTENT)
                popupWindow?.setOnDismissListener {
                    popupWindow = null
                }
            }

            if (popupWindow?.isShowing == true) {
                popupWindow?.dismiss()
            } else {
                popupWindow?.showAsDropDown(ibMoreHoriz, 0, 30)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        popupWindow?.dismiss()
    }
}