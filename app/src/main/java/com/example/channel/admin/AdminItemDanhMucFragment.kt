package com.example.channel.admin


import com.example.channel.R


import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.channel.NgheNgay.ItemDanhMucAdapter
import com.example.channel.NgheNgay.XemTatCaFragment
import com.example.channel.Search.Album
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminItemDanhmucFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: ItemDanhMucAdapter
    private lateinit var adapter2: ItemDanhMucAdapter

    private lateinit var tvAllAlbum2: TextView
    private lateinit var tvTenDanhmuc: TextView
    private lateinit var tvAllAlbum3: TextView
    private  var cate: String =""
    private lateinit var imgBack: ImageView
    val items = arrayListOf<Album>()
    val items2 = arrayListOf<Album>()


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_danhmuc, container, false)
        tvAllAlbum2 = view.findViewById(R.id.tvAllAlbum2)
        tvAllAlbum2.setOnClickListener {
            replaceFragment(XemTatCaFragment(items))
        }
        tvTenDanhmuc = view.findViewById(R.id.txtTenDanhmuc)

        tvAllAlbum3 = view.findViewById(R.id.tvAllAlbum3)
        tvAllAlbum3.setOnClickListener {
            replaceFragment(XemTatCaFragment(items2))
        }
        imgBack = view.findViewById(R.id.imgBack)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val database = Firebase.database


        var reference = database.getReference("categories")
        parentFragmentManager.setFragmentResultListener("send_dm", this) { _, result ->

            parentFragmentManager.beginTransaction().show(this@AdminItemDanhmucFragment)
            val taskDanhmuc = result.getString("tendanhmuc")


            if(taskDanhmuc.equals("Tin tức")){
                tvTenDanhmuc.setText("Tin tức")
                reference= database.getReference("categories").child("category_id_1")
                displayCarousel(reference)
                val reference2 =database.getReference("categories").child("category_id_1")
                displayCarousel2(reference2)

            }
            if(taskDanhmuc.toString().trim().equals("Thể thao")){
                tvTenDanhmuc.setText("Thể thao")
                reference= database.getReference("categories").child("category_id_2")
                displayCarousel(reference)
                val reference2 =database.getReference("categories").child("category_id_2")
                displayCarousel2(reference2)

            }
            if(taskDanhmuc.toString().trim().equals("Hài")){
                tvTenDanhmuc.setText("Hài")
                reference= database.getReference("categories").child("category_id_3")
                displayCarousel(reference)
                val reference2 =database.getReference("categories").child("category_id_3")
                displayCarousel2(reference2)

            }
            if(taskDanhmuc.toString().trim().equals("Kinh doanh")){
                tvTenDanhmuc.setText("Kinh doanh")
                reference= database.getReference("categories").child("category_id_4")
                displayCarousel(reference)
                val reference2 =database.getReference("categories").child("category_id_4")
                displayCarousel2(reference2)

            }
            if(taskDanhmuc.toString().trim().equals("Xã hội và văn hóa")){
                tvTenDanhmuc.setText("Xã hội và văn hóa")
                reference= database.getReference("categories").child("category_id_5")
                displayCarousel(reference)
                val reference2 =database.getReference("categories").child("category_id_5")
                displayCarousel2(reference2)

            }

        }
        imgBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.admin_frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }
    private fun displayCarousel(reference: DatabaseReference){
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (albumSnapshot in snapshot.child("albums").children) {
                    val albumName = albumSnapshot.child("album_name").value as? String
                    val idAlbum = albumSnapshot.key as String
                    val channel = albumSnapshot.child("channel").value as? String
                    val logoAlbum = albumSnapshot.child("logo_album").value as? String
                    Log.d("fridaylog", "The value of logo name is: $logoAlbum")
                    val album = Album(albumName!!, channel!!, logoAlbum!!,idAlbum!!)
                    items.add(album)
                }
                Log.d("hnlog", "The value of myValue is: $items")

                adapter = ItemDanhMucAdapter(items, requireContext())
                adapter.onItemClick = { album ->
                    // Handle click events on album items here
                }
                viewPager = view!!.findViewById<ViewPager2>(R.id.viewPager)
                viewPager.apply {
                    clipChildren = false  // No clipping the left and right items
                    clipToPadding = false  // Show the viewpager in full width without clipping the padding
                    offscreenPageLimit = 3  // Render the left and right items
                    (getChildAt(0) as RecyclerView).overScrollMode =
                        RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
                }
                viewPager.adapter = adapter

                viewPager.setPageTransformer(compositePageTransformer)
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
                Toast.makeText(requireContext(), "Can not get data", Toast.LENGTH_SHORT);
            }
        })
    }
    private fun displayCarousel2(reference: DatabaseReference){
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {



                for (albumSnapshot in snapshot.child("albums").children) {
                    val idAlbum = albumSnapshot.key as String
                    val albumName = albumSnapshot.child("album_name").value as? String
                    val channel = albumSnapshot.child("channel").value as? String
                    val logoAlbum = albumSnapshot.child("logo_album").value as? String
                    val album = Album(albumName!!, channel!!, logoAlbum!!,idAlbum!!)
                    items2.add(album)
                }
                val itemShuffle = items2.shuffled().take(7)

                Log.d("ppp","items: $itemShuffle")
                adapter2 = ItemDanhMucAdapter(itemShuffle as ArrayList<Album>, requireContext())
                viewPager2 = view!!.findViewById<ViewPager2>(R.id.viewPager2)

                viewPager2.apply {
                    clipChildren = false  // No clipping the left and right items
                    clipToPadding = false  // Show the viewpager in full width without clipping the padding
                    offscreenPageLimit = 3  // Render the left and right items
                    (getChildAt(0) as RecyclerView).overScrollMode =
                        RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
                }

                viewPager2.adapter = adapter2

                viewPager2.setPageTransformer(compositePageTransformer)
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
                Toast.makeText(requireContext(), "Can not get data", Toast.LENGTH_SHORT);
            }
        })

    }

}