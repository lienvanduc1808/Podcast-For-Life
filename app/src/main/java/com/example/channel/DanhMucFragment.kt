package com.example.channel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DanhMucFragment : Fragment() {
    private var searchET: TextView? = null
    private var autoCompleteTVAdapter: ArrayAdapter<String>? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: DanhMucAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("hello","hello")
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = view.findViewById(R.id.rvListDanhMuc)
        searchET = view.findViewById(R.id.searchEditText)
        searchET?.setOnClickListener{
            replaceFragment(SearchAlbumFragment())
        }
        val items = arrayListOf<DanhMuc>(DanhMuc("Bảng xếp hạng", "https://firebasestorage.googleapis.com/v0/b/testdb-80aa6.appspot.com/o/Category%2Fimg_11.png?alt=media&token=4fc51f1a-6dc9-4c8b-9bc1-2a18b943b696"))

        val database = Firebase.database
        val reference = database.getReference("categories")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (categorySnapshot in snapshot.children) {

                        val cateName = categorySnapshot.child("cate_name").value as String
                        Log.d("plog", "The value of myValue is: $cateName")

                        val cateImage = categorySnapshot.child("cate_image").value as String
                        val category = DanhMuc(cateName, cateImage)
                        items.add(category)
                    }
                items.add(DanhMuc("Dramas", ""))
                items.add(DanhMuc("Nghệ thuật", ""))
                    Log.d("plog", "The value of myValue is: $items")
                adapter = DanhMucAdapter(items,requireContext())
                recyclerView!!.adapter = adapter
                recyclerView!!.layoutManager = GridLayoutManager( context, 2)
              //  setUpAutoCompleteTVAdapter(DanhMucList.getDanhMucNameList())

                adapter?.onItemClick = { danhMuc ->
                    when (danhMuc.name) {
                        "New Fragment 1" -> {
                            replaceFragment(RankingTableFragment())
                        }
                        "Tin tức" -> {
                            val fragment = ItemDanhmucFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@DanhMucFragment)
                                .commit()

                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.name )
                            }
                            parentFragmentManager.setFragmentResult("send_dm", send_data)


                        }
                        "Thể thao" -> {
                            val fragment = ItemDanhmucFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@DanhMucFragment)
                                .commit()

                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.name )

                            }
                            parentFragmentManager.setFragmentResult("send_dm", send_data)


                        }
                        "Hài" -> {
                            val fragment = ItemDanhmucFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@DanhMucFragment)
                                .commit()

                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.name )

                            }
                            parentFragmentManager.setFragmentResult("send_dm", send_data)


                        }
                        "Kinh doanh" -> {
                            val fragment = ItemDanhmucFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@DanhMucFragment)
                                .commit()

                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.name )

                            }
                            parentFragmentManager.setFragmentResult("send_dm", send_data)


                        }
                        "Xã hội và văn hóa" -> {
                            val fragment = ItemDanhmucFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@DanhMucFragment)
                                .commit()

                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.name )

                            }
                            parentFragmentManager.setFragmentResult("send_dm", send_data)


                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
                Toast.makeText(requireContext(), "Can not get data", Toast.LENGTH_SHORT);
            }
        })


        return view
    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }
}