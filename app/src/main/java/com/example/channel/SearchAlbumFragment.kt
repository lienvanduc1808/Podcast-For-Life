package com.example.channel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SearchAlbumFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlbumAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_album, container, false)
        searchView = view.findViewById(R.id.search_view)
        recyclerView = view.findViewById(R.id.search_results)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AlbumAdapter(emptyList())
        recyclerView.adapter = adapter
        searchView.setOnQueryTextListener(this)
        return view
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val results = performSearch(newText)
        adapter.setItems(results)
        Log.d("elog", "The value of myValue is: $newText")
        return true
    }

    private fun performSearch(searchTerm: String? ): List<Album> {
        // Perform search and return list of search results
      //  val allAlbums = getAllAlbums() // Get all the albums from your data source
        val database = Firebase.database
        val reference = database.getReference("categories")
        val allAlbums = arrayListOf<Album>()
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (categorySnapshot in snapshot.children) {

                    for (albumSnapshot in categorySnapshot.child("albums").children) {
                        val albumName = albumSnapshot.child("album_name").value as? String
                        val channel = albumSnapshot.child("channel").value as? String
                        val logoAlbum = albumSnapshot.child("logo_album").value as? String
                        if (albumName != null && channel != null && logoAlbum != null) {
                            val album = Album(albumName, channel, logoAlbum)
                            allAlbums.add(album)
                        }
                    }

                    Log.d("hnlog", "The value of myValue is: $allAlbums")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
                Toast.makeText(requireContext(), "Can not get data", Toast.LENGTH_SHORT);
            }
        })

        val results = mutableListOf<Album>()
        for (album in allAlbums) {
            Log.d("alog", "The value of search is: $searchTerm")
            Log.d("blog", "The value of search is: ${album.name}")
            if (searchTerm?.let { album.name.contains(it, ignoreCase = true) } == true) {
                results.add(album)
            }
        }
        return results
    }

}
