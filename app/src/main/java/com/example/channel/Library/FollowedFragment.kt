package com.example.channel.Library

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.channel.NgheNgay.XemTatCaAdapter
import com.example.channel.NgheNgay.XemTatCaAlbumAdapter
import com.example.channel.NgheNgay.albumData
import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class FollowedFragment : Fragment() {
    private lateinit var returnLibraryBtn: ImageButton
    private lateinit var rvListSubcribed: RecyclerView
    private lateinit var fragmentContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_followed, container, false)
        rvListSubcribed = view.findViewById(R.id.rvListSubcribed)
        returnLibraryBtn = view.findViewById(R.id.ibReturnLibrary)
        returnLibraryBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var items = arrayListOf<albumData>()

        val auth: FirebaseAuth
        val storageReference: StorageReference
        val userDatabase: DatabaseReference
        val AlbumDatabase: DatabaseReference

        auth = FirebaseAuth.getInstance()
        userDatabase = FirebaseDatabase.getInstance().getReference("users/${auth.currentUser?.uid}/subcribed")
        AlbumDatabase = FirebaseDatabase.getInstance().getReference("categories/")

        userDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val SubcribeChildrenList = dataSnapshot.children.map { it.key as String }
                var subSize = SubcribeChildrenList.size
                Log.i("saved", SubcribeChildrenList.toString())

                AlbumDatabase.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(categories: DataSnapshot) {
                        for(category in categories.children){
                            if(subSize == 0){
                                break
                            }
                            for(album in category.child("albums").children){
                                if(subSize == 0){
                                    break
                                }
                                for(sub in SubcribeChildrenList){
                                    if(subSize == 0){
                                        break
                                    }
                                    if(album.key.toString().equals(sub)){
                                        val albumName = album.child("album_name").value.toString()
                                        val albumChannel = album.child("channel").value.toString()
                                        val albumDescription = album.child("description").value.toString()
                                        val albumImage = album.child("logo_album").value.toString()
                                        items.add(albumData(albumName, albumChannel, albumDescription, albumImage))
                                        subSize -= 1
                                    }
                                }
                            }
                        }
                        Log.i("items", items.toString())
                        val adapter = XemTatCaAlbumAdapter(items, fragmentContext)
                        rvListSubcribed.adapter = adapter
                        val layoutManager = GridLayoutManager(fragmentContext, 2)
                        rvListSubcribed.layoutManager = layoutManager
                    }



                    override fun onCancelled(error: DatabaseError) {
                        //
                    }

                })
            }

            override fun onCancelled(error: DatabaseError) {
                //
            }

        })
    }
}