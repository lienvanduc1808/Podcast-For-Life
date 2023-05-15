package com.example.channel

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.channel.Search.DanhMuc
import com.example.channel.admin.DanhMucAdminAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AlbumManageFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var adapter: DanhMucAdminAdapter? = null
    private lateinit var fragmentContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_admin_manage_album, container, false)
        recyclerView = view.findViewById(R.id.rvListDm)

        val items = arrayListOf<DanhMuc>()

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

                Log.d("plog", "The value of myValue is: $items")
                adapter = DanhMucAdminAdapter(items,fragmentContext)
                recyclerView!!.adapter = adapter
                recyclerView!!.layoutManager = GridLayoutManager( context, 2)


                adapter?.onItemClick = { danhMuc ->

                    when (danhMuc.cate_name) {

                        "Tin tức" -> {
                            val fragment = ItemAlbumManageFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.admin_frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@AlbumManageFragment)
                                .commit()


                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.cate_name )
                            }
                            parentFragmentManager.setFragmentResult("send_dm", send_data)


                        }
                        "Thể thao" -> {
                            val fragment = ItemAlbumManageFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.admin_frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@AlbumManageFragment)
                                .commit()

                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.cate_name )

                            }
                            parentFragmentManager.setFragmentResult("send_dm", send_data)


                        }
                        "Hài" -> {
                            val fragment = ItemAlbumManageFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.admin_frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@AlbumManageFragment)
                                .commit()

                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.cate_name )

                            }
                            parentFragmentManager.setFragmentResult("send_dm", send_data)


                        }
                        "Kinh doanh" -> {
                            val fragment = ItemAlbumManageFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.admin_frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@AlbumManageFragment)
                                .commit()

                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.cate_name )

                            }
                            parentFragmentManager.setFragmentResult("send_dm", send_data)


                        }
                        "Xã hội và văn hóa" -> {
                            val fragment = ItemAlbumManageFragment()
                            parentFragmentManager.beginTransaction()
                                .add(R.id.admin_frame_layout, fragment)
                                .addToBackStack(null)
                                .hide(this@AlbumManageFragment)
                                .commit()

                            val send_data = Bundle().apply {
                                putString("tendanhmuc",danhMuc.cate_name )

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