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

class XemTatCaFragment(private val albumList: ArrayList<Album>) : Fragment() {
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
        val items = arrayListOf<Album>()
//        val database = Firebase.database
//
//        val reference = database.getReference("categories")
//
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                for (categorySnapshot in snapshot.children) {
//
//                    for (albumSnapshot in categorySnapshot.child("albums").children) {
//                        val albumName = albumSnapshot.child("album_name").value as String
//
//                        val channel = albumSnapshot.child("channel").value as String
//                        val logoAlbum = albumSnapshot.child("logo_album").value as String
//                        val album = Album(albumName, channel, logoAlbum)
//                        Log.d("wlog", "The value items: $album")
//                        items.add(album)
//                    }
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Xử lý lỗi
//            }
//        })
        rvListAlbum.adapter = XemTatCaAdapter(albumList, requireContext())
        rvListAlbum.layoutManager = GridLayoutManager(context, 2)
    }
}