package com.example.channel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DownloadedFragment : Fragment() {
    private lateinit var ibReturnLibrary: ImageButton
    private lateinit var rvListAlbumDownloaded: RecyclerView
    private lateinit var ibMoreHoriz2: ImageButton
    private lateinit var ivCheckHide: ImageView
    private lateinit var clHideEpisode: ConstraintLayout

    private var popupWindow: PopupWindow? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_downloaded, container, false)
        ibReturnLibrary = view.findViewById(R.id.ibReturnLibrary)
        ibReturnLibrary.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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


        ibMoreHoriz2 = view.findViewById(R.id.ibMoreHoriz2)
        ibMoreHoriz2?.setOnClickListener {
            if(popupWindow == null){
                val popupView = LayoutInflater.from(context).inflate(R.layout.popup_menu_downloaded, null)
                popupWindow = PopupWindow(popupView,900, ViewGroup.LayoutParams.WRAP_CONTENT)
                popupWindow?.setOnDismissListener {
                    popupWindow = null
                }

                clHideEpisode = popupView.findViewById(R.id.clHideEpisode)
                ivCheckHide = popupView.findViewById(R.id.ivCheckHide)
                clHideEpisode?.setOnClickListener {
                    ivCheckHide.setImageResource(R.drawable.bg_btn_latest)
                }

            }

            if (popupWindow?.isShowing == true) {
                popupWindow?.dismiss()
            } else {
                popupWindow?.showAsDropDown(ibMoreHoriz2, 0, 30)
            }
        }



    }

    override fun onPause() {
        super.onPause()
        popupWindow?.dismiss()
    }
}