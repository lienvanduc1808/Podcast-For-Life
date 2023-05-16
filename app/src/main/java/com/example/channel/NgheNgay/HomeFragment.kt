package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.channel.NgheNgay.HomeAdapter
import com.example.channel.NgheNgay.XemTatCaFragment
import com.example.channel.R
import com.example.channel.Search.Album
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
    val items = arrayListOf<Album>()
    val items2 = arrayListOf<Album>()
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tvAllAlbum2 = view.findViewById(R.id.tvAllAlbum2)
        tvAllAlbum2.setOnClickListener {
            replaceFragment(XemTatCaFragment(items))
        }

        tvAllAlbum3 = view.findViewById(R.id.tvAllAlbum3)
        tvAllAlbum3.setOnClickListener {
            replaceFragment(XemTatCaFragment(items2))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))

        val database = Firebase.database
        val reference = database.getReference("categories")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (categorySnapshot in snapshot.children) {

                    for (albumSnapshot in categorySnapshot.child("albums").children) {
                        val idAlbum =  albumSnapshot.key.toString()
                        val albumName = albumSnapshot.child("album_name").value as String

                        val channel = albumSnapshot.child("channel").value as String
                        val logoAlbum = albumSnapshot.child("logo_album").value as String
                        Log.d("fridaylog", "The value of logo name is: $logoAlbum")
                        val album = Album(albumName, channel, logoAlbum,idAlbum)
                        items.add(album)




                    }
                    Log.d("hnlog", "The value of myValue is: $items")
                }
                adapter = HomeAdapter(items, fragmentContext)
                adapter.onItemClick = { album ->
                    // Handle click events on album items here



                }
                viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
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

        val reference2 = database.getReference("categories")

        reference2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (categorySnapshot in snapshot.children) {

                    for (albumSnapshot in categorySnapshot.child("albums").children) {
                        val idAlbum =  albumSnapshot.key.toString()
                        val albumName = albumSnapshot.child("album_name").value as String
                        val channel = albumSnapshot.child("channel").value as String
                        val logoAlbum = albumSnapshot.child("logo_album").value as String
                        val album = Album(albumName, channel, logoAlbum,idAlbum)
                        items2.add(album)
                    }
                }

                val itemShuffle = items2.shuffled().take(20)


                adapter2 = HomeAdapter(itemShuffle as ArrayList<Album>,fragmentContext)
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

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }
}


