package com.example.channel

import android.annotation.SuppressLint
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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
        val items = listOf<Album>()
        val database = Firebase.database

        val reference = database.getReference("categories")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (categorySnapshot in snapshot.children) {
                    Log.d("hnplog", "The value items: $categorySnapshot")
                    for (albumSnapshot in categorySnapshot.child("albums").children) {
                        val albumName = albumSnapshot.child("album_name").value as String

                        val channel = albumSnapshot.child("channel").value as String
                        val logoAlbum = albumSnapshot.child("logo_album").value as String
                        val album = Album(albumName, channel, logoAlbum)

                        items.plus(album)
                    }

                }
                rvListAlbum.adapter = XemTatCaAdapter(items, requireContext())
                rvListAlbum.layoutManager = GridLayoutManager(context, 2)
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
            }
        })





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