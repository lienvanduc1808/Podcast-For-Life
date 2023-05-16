package com.example.channel.Library

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import com.example.channel.NgheNgay.ListTapAdapter
import com.example.channel.NgheNgay.ListTapData
import com.example.channel.NgheNgay.episodeData
import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class SavedFragment : Fragment() {
    private lateinit var returnLibraryBtn: ImageButton
    private lateinit var listViewSaved: ListView
    private lateinit var listSavedAdapter: ListSavedAdapter
    private lateinit var fragmentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)
        returnLibraryBtn = view.findViewById(R.id.ibReturnLibrary)
        returnLibraryBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth: FirebaseAuth
        val storageReference: StorageReference
        val userDatabase: DatabaseReference
        val episodeDatabase: DatabaseReference

        auth = FirebaseAuth.getInstance()
        userDatabase = FirebaseDatabase.getInstance().getReference("users/${auth.currentUser?.uid}/saved")
        episodeDatabase = FirebaseDatabase.getInstance().getReference("categories/")

        userDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val savedChildrenList = dataSnapshot.children.map { it.value as String }
                var savedSize = savedChildrenList.size
                Log.i("saved", savedChildrenList.toString())

                episodeDatabase.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(categories: DataSnapshot) {
                        var listSavedEpisode = mutableListOf<ListTapData>()

                        for(category in categories.children){
                            Log.i("size", savedSize.toString())
                            if(savedSize == 0){
                                break
                            }
                            val albumsList = category.child("albums")
                            val al = albumsList.children.toList()
                            for(dl in  savedChildrenList){
                                if(savedSize == 0){
                                    break
                                }
                                var renewDl = dl.substring(0, dl.lastIndexOf("e"))
                                val indexAlbum = binarySearch(renewDl, al)
                                if (indexAlbum != -1){
                                    Log.i("right", renewDl)

                                    var es = al[indexAlbum].child("episodes").children.toList()
                                    var indexEpisode = binarySearch(dl, es)
                                    if(indexEpisode != -1){
                                        Log.i("righttttt", dl)
                                        var date = es[indexEpisode].child("date").value.toString()
                                        var descript = es[indexEpisode].child("descript").value.toString()
                                        var img = es[indexEpisode].child("img").value.toString()
                                        var title = es[indexEpisode].child("title").value.toString()
                                        var _id = es[indexEpisode].key.toString()
//                                        listSavedEpisode.add(episodeData(title, descript, date, img))
                                        listSavedEpisode.add(ListTapData(_id, date, title, descript, img, ""))
                                        savedSize -= 1
                                    }
                                }
                            }

                        }
                        Log.i("listEp", listSavedEpisode.toString())
                        listViewSaved = view.findViewById(R.id.lvSaved)
                        listSavedAdapter = ListSavedAdapter(fragmentContext, R.layout.item_episode_saved, listSavedEpisode)
                        listViewSaved.adapter = listSavedAdapter

                    }


                    override fun onCancelled(error: DatabaseError) {
                        Log.e("error", "Lỗi khi đọc danh sách các DataSnapshot con", error.toException())
                    }
                })


                // Xử lý các DataSnapshot con ở đây
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("error", "Lỗi khi đọc danh sách các DataSnapshot con", databaseError.toException())
            }
        })

//        listViewSaved = view.findViewById(R.id.lvSaved)
//        val items = listOf(
//            episodeData(R.drawable.trikycamxuc,"16 THÁNG 3", "#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
//            episodeData(R.drawable.trikycamxuc, "12 tháng 3","#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
//            episodeData(R.drawable.trikycamxuc, "15 tháng 2","#25 - người lớn cô đơn", "Mình là Giang, mình là người lớn và mình thỉnh thoảng cũng cô đơn"),
//        )
//        listSavedAdapter = ListSavedAdapter(requireContext(), R.layout.list_opisode, items)
//        listViewSaved.adapter = listSavedAdapter

    }

    fun binarySearch(currentId: String, sortedList:List<DataSnapshot>): Int{
        var left = 0
        var right = sortedList.size - 1
        var mid: Int

        while (left <= right) {
            mid = (left + right) / 2
            val id = sortedList[mid].key as String
            when {
                currentId < id -> right = mid - 1
                currentId > id -> left = mid + 1
                else -> return mid
            }
        }

        return -1
    }

    override fun onPause() {
        super.onPause()
        listSavedAdapter.dismissAllPopups()
    }

}