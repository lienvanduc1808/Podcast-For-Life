package com.example.channel

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.channel.Search.Album
import com.example.channel.admin.XemTatCaAlbumAdapter
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ItemAlbumManageFragment : Fragment() {
    private lateinit var tvTenDanhmuc: TextView

    private lateinit var rvListAlbum: RecyclerView
    private lateinit var imgBack:ImageView
    private var taskDanhmuc: String = ""
    val items = arrayListOf<Album>()

    private lateinit var fragmentContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_album_manage, container, false)




        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = Firebase.database
        rvListAlbum = view.findViewById(R.id.adminListAlbumRV)
        tvTenDanhmuc = view.findViewById(R.id.textView6)
        imgBack = view.findViewById(R.id.imgBack)
        imgBack.setOnClickListener {
                parentFragmentManager!!.popBackStack()

        }
        Log.d("fdddd","ffdfsdfs")


        var reference = database.getReference("categories")
        parentFragmentManager.setFragmentResultListener("send_dm", this) { _, result ->
        Log.d("ffsf","sdfsdfsfsdfdsf")
            parentFragmentManager.beginTransaction().show(this@ItemAlbumManageFragment)
            taskDanhmuc = result.getString("tendanhmuc")!!


            if(taskDanhmuc.toString().trim().equals("Tin tức")){
                tvTenDanhmuc.setText("Tin tức")
                reference= database.getReference("categories").child("category_id_1")
                displayAlbum(reference)







            }
            if(taskDanhmuc.toString().trim().equals("Thể thao")){
                tvTenDanhmuc.setText("Thể thao")
                reference= database.getReference("categories").child("category_id_2")
                displayAlbum(reference)



            }
            if(taskDanhmuc.toString().trim().equals("Hài")){
                tvTenDanhmuc.setText("Hài")
                reference= database.getReference("categories").child("category_id_3")
                displayAlbum(reference)


            }
            if(taskDanhmuc.toString().trim().equals("Kinh doanh")){
                tvTenDanhmuc.setText("Kinh doanh")
                reference= database.getReference("categories").child("category_id_4")
                displayAlbum(reference)


            }
            if(taskDanhmuc.toString().trim().equals("Xã hội và văn hóa")){
                tvTenDanhmuc.setText("Xã hội và văn hóa")
                reference= database.getReference("categories").child("category_id_5")
                displayAlbum(reference)


            }






        }












    }

    private fun displayAlbum(reference: DatabaseReference){

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                items.clear()
                for (albumSnapshot in snapshot.child("albums").children) {
                    val albumName = albumSnapshot.child("album_name").value as? String
                    val idAlbum = albumSnapshot.key as String
                    val channel = albumSnapshot.child("channel").value as? String
                    val logoAlbum = albumSnapshot.child("logo_album").value as? String
                    Log.d("fridaylog", "The value of logo name is: $logoAlbum")
                    val album = Album(albumName!!, channel!!, logoAlbum!!,idAlbum!!+","+taskDanhmuc)
                    items.add(album)
                    Log.d("items",items.toString())
                    rvListAlbum.adapter = XemTatCaAlbumAdapter(items, fragmentContext)
                    rvListAlbum.layoutManager = GridLayoutManager(context, 2)






                }



            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
                Toast.makeText(requireContext(), "Can not get data", Toast.LENGTH_SHORT);
            }
        })
        Log.d("reference",reference.toString())
    }








    }
