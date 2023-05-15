package com.example.channel.admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.channel.*
import com.example.channel.Search.DanhMuc
import com.example.channel.admin.CateManageAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CateManageFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adapter: CateManageAdapter? = null
    private var btnAddCate: Button? = null

    private lateinit var fragmentContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_admin_cate_manage, container, false)
        recyclerView = view.findViewById(R.id.adminListCateRV)
        btnAddCate = view.findViewById(R.id.btn_add_cate)
        btnAddCate!!.setOnClickListener{
            replaceFragment(AdminAddCateFragment())
        }


        recyclerView?.layoutManager = LinearLayoutManager(fragmentContext)
        val items = arrayListOf<DanhMuc>()

        val database = Firebase.database
        val reference = database.getReference("categories")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter?.clearData()
                for (categorySnapshot in snapshot.children) {

                    val cateName = categorySnapshot.child("cate_name").value as String

                    val cateImage = categorySnapshot.child("cate_image").value as String
                    val category = DanhMuc(cateName, cateImage)
                    items.add(category)
                }
                Log.d("calog", "The value of search is: $items")
                adapter = CateManageAdapter(items, fragmentContext)

                recyclerView!!.adapter = adapter
                adapter?.onItemClick = { danhMuc ->
                    when (danhMuc.cate_name) {
                        "Tin tức" -> {
                            adminReplaceFragment(danhMuc.cate_name)

                        }
                        "Thể thao" -> {

                            adminReplaceFragment(danhMuc.cate_name)
                        }
                        "Hài" -> {

                            adminReplaceFragment(danhMuc.cate_name)
                        }
                        "Kinh doanh" -> {
                            adminReplaceFragment(danhMuc.cate_name)
                        }
                        "Xã hội và văn hóa" -> {
                            adminReplaceFragment(danhMuc.cate_name)
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


//    private fun replaceFragment(name: String){
//        val fragment = ItemDanhmucFragment()
//        parentFragmentManager.beginTransaction()
//            .add(R.id.admin_frame_layout, fragment)
//            .addToBackStack(null)
//            .hide(this@CateManageFragment)
//            .commit()
//
//        val send_data = Bundle().apply {
//            putString("tendanhmuc",name)
//
//        }
//        parentFragmentManager.setFragmentResult("send_dm", send_data)
//    }
//
    private fun adminReplaceFragment(name: String){
        val fragment = AdminItemDanhmucFragment()
        parentFragmentManager.beginTransaction()
            .add(R.id.admin_frame_layout, fragment)
            .addToBackStack(null)
            .hide(this@CateManageFragment)
            .commit()

        val send_data = Bundle().apply {
            putString("tendanhmuc",name )

        }
        parentFragmentManager.setFragmentResult("send_dm", send_data)

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.admin_frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }
}
