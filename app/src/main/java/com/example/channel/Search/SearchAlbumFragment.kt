package com.example.channel.Search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.channel.NgheNgay.NgheNgayFragment
import com.example.channel.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SearchAlbumFragment : Fragment(), SearchView.OnQueryTextListener,  AlbumAdapter.OnItemClickListener {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlbumAdapter
    private lateinit var txtHuy: TextView

    override fun onItemClick(album: Album) {
        TODO("Not yet implemented")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_album, container, false)
        searchView = view.findViewById(R.id.search_view)
        recyclerView = view.findViewById(R.id.search_results)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AlbumAdapter(emptyList(), object : AlbumAdapter.OnItemClickListener {
            override fun onItemClick(album: Album) {
                // Handle album click event here
                searchView.setQuery(album.name, true)

                (context as AppCompatActivity).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, NgheNgayFragment())
                    .addToBackStack(null)
                    .commit()

                val send_data = Bundle().apply {
                    putString("idAlbum", album.id_album.toString())
                    Log.d("idAlbum",album.id_album.toString())

                }
                (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)
            }
        })

        recyclerView.adapter = adapter
        searchView.setOnQueryTextListener(this)

        txtHuy = view.findViewById(R.id.txtHuy)
        txtHuy.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(searchTerm: String?): Boolean {
        //val results = performSearch(newText)
        Log.d("alog", "The value of search is: $searchTerm")
        val database = Firebase.database
        val reference = database.getReference("categories")
        val allAlbums = arrayListOf<Album>()
        val results = mutableListOf<Album>()
        //var resultsOff = mutableListOf<Album>()
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (categorySnapshot in snapshot.children) {

                    for (albumSnapshot in categorySnapshot.child("albums").children) {
                        Log.d("allog", "The value of id album is: ${albumSnapshot.key}")
                        val idAlbum =  albumSnapshot.key as String
                        val albumName = albumSnapshot.child("album_name").value as? String
                        val channel = albumSnapshot.child("channel").value as? String
                        val logoAlbum = albumSnapshot.child("logo_album").value as? String
                        if (albumName != null && channel != null && logoAlbum != null) {
                            val album = Album(albumName, channel, logoAlbum,idAlbum!!)
                            allAlbums.add(album)
                            Log.d("pplog", "The value of myValue is: $album")
                        }

                    }
                }

                Log.d("pthnlog", "The value of myValue is: $allAlbums")
                for (album in allAlbums) {

                    Log.d("blog", "The value of search is: ${album.name}")
                    if (searchTerm?.let { album.name.contains(it, ignoreCase = true) } == true) {
                        results.add(album)
                    }
                }
                Log.d("mmlog", "The value of myValue is: $results")
                adapter.setItems(results)
                Log.d("elog", "The value of myValue is: $results")
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
                Toast.makeText(requireContext(), "Can not get data", Toast.LENGTH_SHORT);
            }
        })


        return true
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }



}
