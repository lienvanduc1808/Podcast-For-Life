package com.example.channel.Library

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import com.example.channel.ListSavedAdapter
import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class SavedFragment : Fragment() {
    private lateinit var returnLibraryBtn: ImageButton
    private lateinit var listViewSaved: ListView
    private lateinit var listSavedAdapter: ListSavedAdapter
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
        userDatabase = FirebaseDatabase.getInstance().getReference("users/${auth.currentUser?.uid}/downloaded")
        episodeDatabase = FirebaseDatabase.getInstance().getReference("categories/category_id_2/")

        userDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val downloadedChildrenList = dataSnapshot.children.map { it.value as String }
                Log.i("saved", downloadedChildrenList.toString())
                val albumsList = episodeDatabase.child("albums")
                albumsList.orderByChild("albums").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val al = dataSnapshot.children.toList()
                        var listSavedEpisode = mutableListOf<tapData>()
                        Log.i("al", al.toString())
                        for(dl in  downloadedChildrenList){
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
                                    listSavedEpisode.add(tapData(date, descript, img, title))
                                }
                            }
                        }

                        Log.i("listEp", listSavedEpisode.toString())
                        listViewSaved = view.findViewById(R.id.lvSaved)
                        listSavedAdapter = ListSavedAdapter(requireContext(), R.layout.list_opisode, listSavedEpisode)
                        listViewSaved.adapter = listSavedAdapter

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e("error", "Lỗi khi đọc danh sách các DataSnapshot con", databaseError.toException())
                    }
                })

                Log.i("downloaded", downloadedChildrenList.toString())
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