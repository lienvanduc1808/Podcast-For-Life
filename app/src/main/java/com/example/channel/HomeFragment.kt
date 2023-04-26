package com.example.channel

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: HomeAdapter
    private lateinit var adapter2: HomeAdapter

    private lateinit var tvAllAlbum2: TextView
    private lateinit var tvAllAlbum3: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tvAllAlbum2 = view.findViewById(R.id.tvAllAlbum2)
        tvAllAlbum2.setOnClickListener {
           // replaceFragment(XemTatCaFragment())
        }

        tvAllAlbum3 = view.findViewById(R.id.tvAllAlbum3)
        tvAllAlbum3.setOnClickListener {
            //replaceFragment(XemTatCaFragment())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = arrayListOf<Album>()
        val items2 = arrayListOf<Album>()
        val database = Firebase.database
//        val myRef = database.getReference("/categories/category_id_1/albums")
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (postSnapshot in dataSnapshot.children) {
//                    val list = postSnapshot.getValue(Album::class.java)
//                    items.add(list!!)
//
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//              //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//                // ...
//                Toast.makeText(requireContext(), "get ok", Toast.LENGTH_SHORT)
//            }
//        })
//
//        val myRef2 = database.getReference("/categories/category_id_2/albums")
//        myRef2.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (postSnapshot in dataSnapshot.children) {
//                    val list = postSnapshot.getValue(Album::class.java)
//                    items.add(list!!)
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//                // ...
//            }
//        })


        val reference = database.getReference("categories")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (categorySnapshot in snapshot.children) {

                    for (albumSnapshot in categorySnapshot.child("albums").children) {
                        val albumName = albumSnapshot.child("album_name").value as String
                        Log.d("hnlog", "The value of myValue is: $albumName")
                        val channel = albumSnapshot.child("channel").value as String
                        val logoAlbum = albumSnapshot.child("logo_album").value as String
                        val album = Album(albumName, channel, logoAlbum)
                        items.add(album)
                    }
                    Log.d("hnlog", "The value of myValue is: $items")
                }
                // Làm sao để truyền vào ViewPager?
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
            }
        })

        val reference2 = database.getReference("categories")

        reference2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (categorySnapshot in snapshot.children) {

                    for (albumSnapshot in categorySnapshot.child("albums").children) {
                        val albumName = albumSnapshot.child("album_name").value as String
                        val channel = albumSnapshot.child("channel").value as String
                        val logoAlbum = albumSnapshot.child("logo_album").value as String
                        val album = Album(albumName, channel, logoAlbum)
                        items2.add(album)
                    }
                }
                // Làm sao để truyền vào ViewPager?
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
            }
        })
//        val items = arrayListOf(
//            Album("Lời khuyên hữu ích", "The Present Writer", R.drawable.img_9),
//            Album("Marketing", "Vietcetera", R.drawable.img_8),
//            Album("Tay mơ học đời", "Amateur Psychology",R.drawable.img_7 ),
//            Album("Bình thường một cách bất thường ", "Oddly Normal", R.drawable.img_6),
//            Album("sức khỏe tâm lý.", "Tâm Lý Học Tuổi Trẻ", R.drawable.img_5),
//            Album(" mọi chuyện trong cuộc sống", "Spiderum",R.drawable.img_4 ),
//            Album("câu chuyện lịch sử", "Bí Ẩn Sử Việt",R.drawable.img_3 ),
//            Album("lắng nghe và chia sẻ ", "Radio Người Giữ Kỉ Niệm", R.drawable.img_2),
//            Album("Vipassana", "Minh Niệm", R.drawable.img_1),
//            Album("những bệnh lý thời hiện đạ", "Optimal Health Daily",R.drawable.img )
//        )
//
//        val items2 = arrayListOf(
//            Album("Lời khuyên hữu ích", "The Present Writer", R.drawable.img_9),
//            Album("Marketing", "Vietcetera", R.drawable.img_8),
//            Album("Tay mơ học đời ", "Amateur Psychology",R.drawable.img_7 ),
//            Album("Bình thường một cách bất thường ", "Oddly Normal", R.drawable.img_6),
//            Album("sức khỏe tâm lý.", "Tâm Lý Học Tuổi Trẻ", R.drawable.img_5),
//            Album(" mọi chuyện trong cuộc sống", "Spiderum",R.drawable.img_4 ),
//            Album("câu chuyện lịch sử", "Bí Ẩn Sử Việt",R.drawable.img_3 ),
//            Album("lắng nghe và chia sẻ ", "Radio Người Giữ Kỉ Niệm", R.drawable.img_2),
//            Album("Vipassana", "Minh Niệm", R.drawable.img_1),
//            Album("những bệnh lý thời hiện đạ", "Optimal Health Daily",R.drawable.img )
//        )
        adapter = HomeAdapter(items, requireContext())
        adapter2 = HomeAdapter(items2, requireContext())
        adapter.onItemClick = { album ->
            // Handle click events on album items here
        }
        viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager2)
        viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }
        viewPager2.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }
        viewPager.adapter = adapter
        viewPager2.adapter = adapter2
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))
        viewPager.setPageTransformer(compositePageTransformer)
        viewPager2.setPageTransformer(compositePageTransformer)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }
}


