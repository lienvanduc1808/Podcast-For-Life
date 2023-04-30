package com.example.channel.NgheNgay

import com.example.channel.R
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
    private var autoCompleteTV: AutoCompleteTextView? = null
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
        autoCompleteTV = view.findViewById(R.id.search_view)
        val items = arrayListOf<categoryData>()

        val database = Firebase.database
        val reference = database.getReference("categories")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (categorySnapshot in snapshot.children) {

                    val cateName = categorySnapshot.child("cate_name").value as String
                    Log.d("plog", "The value of myValue is: $cateName")

                    val cateImage = categorySnapshot.child("cate_image").value as String
                    val category = categoryData(cateName, cateImage)
                    items.add(category)
                }
                Log.d("plog", "The value of myValue is: $items")
                adapter = DanhMucAdapter(items,requireContext())
                recyclerView!!.adapter = adapter
                recyclerView!!.layoutManager = GridLayoutManager( context, 2)
                //  setUpAutoCompleteTVAdapter(DanhMucList.getDanhMucNameList())
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
                Toast.makeText(requireContext(), "Can not get data", Toast.LENGTH_SHORT);
            }
        })
        return view
    }

    fun setUpAutoCompleteTVAdapter(data: ArrayList<String>) {
        autoCompleteTVAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data
        )
        autoCompleteTV!!.setAdapter(autoCompleteTVAdapter)
        autoCompleteTV!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val foundDanhMuc: ArrayList<categoryData> =
                    DanhMucList.getDanhMucList()
                        .filter { s ->
                            s.name.contains(
                                (p0.toString()),
                                true
                            )
                        } as ArrayList<categoryData >
                adapter = DanhMucAdapter(foundDanhMuc,requireContext())
                recyclerView!!.adapter = adapter
                recyclerView!!.layoutManager = GridLayoutManager(context, 2)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        Log.i("continue", "view list continue")
        val text: Editable = autoCompleteTV!!.text
        autoCompleteTV!!.setText(text)
        autoCompleteTV!!.setSelection(text.length)

        //adapter!!.notifyDataSetChanged()
        // setUpAutoCompleteTVAdapter(DanhMucList.getDanhMucNameList())
    }
}






//
//old code
//
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import android.widget.AutoCompleteTextView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.channel.R
//
//class DanhMucFragment : Fragment() {
//    private var autoCompleteTV: AutoCompleteTextView? = null
//    private var autoCompleteTVAdapter: ArrayAdapter<String>? = null
//    private var recyclerView: RecyclerView? = null
//    private var adapter: DanhMucAdapter? = null
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        Log.e("hello","hello")
//        val view = inflater.inflate(R.layout.fragment_search, container, false)
//        recyclerView = view.findViewById(R.id.rvListDanhMuc)
//        autoCompleteTV = view.findViewById(R.id.search_view)
//        val items = listOf(
//            categoryData("Text 1.1", R.drawable.tinhban),
//            categoryData("Text 1.2", R.drawable.tinhban),
//            categoryData("Text 1.3", R.drawable.tinhban),
//            categoryData("Text 1.4", R.drawable.tinhban),
//            categoryData("Text 1.5", R.drawable.tinhban),
//            categoryData("Text 1.6", R.drawable.tinhban),
//            categoryData("Text 1.7", R.drawable.tinhban),
//            categoryData("Text 1.8", R.drawable.tinhban),
//
//            // add more items here
//        )
//        val danhMucNameListForSearch = listOf(
//            categoryData("Text 1.1", R.drawable.tinhban),
//            categoryData("Text 1.2", R.drawable.tinhban),
//            categoryData("Text 1.3", R.drawable.tinhban),
//            categoryData("Text 1.4", R.drawable.tinhban),
//            categoryData("Text 1.5", R.drawable.tinhban),
//            categoryData("Text 1.6", R.drawable.tinhban),
//            categoryData("Text 1.7", R.drawable.tinhban),
//            categoryData("Text 1.8", R.drawable.tinhban),
//
//            )
//        //DanhMucList.setListData(danhMucNameListForSearch)
//
//        adapter = DanhMucAdapter(items)
//        recyclerView!!.adapter = adapter
//        recyclerView!!.layoutManager = GridLayoutManager( context, 2)
//        var a = adapter!!.itemCount
//        Log.e("hoang",a.toString())
////        recyclerView!!.addItemDecoration(
////            DividerItemDecoration( requireContext(),
////                DividerItemDecoration.HORIZONTAL
////            )
////        )
////        setUpAutoCompleteTVAdapter(DanhMucList.getDanhMucNameList())
//
//        return view
//    }
//
//    fun setUpAutoCompleteTVAdapter(data: ArrayList<String>) {
//        autoCompleteTVAdapter = ArrayAdapter<String>(
//            requireContext(),
//            android.R.layout.simple_list_item_1,
//            data
//        )
//        autoCompleteTV!!.setAdapter(autoCompleteTVAdapter)
//        autoCompleteTV!!.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                val foundDanhMuc: ArrayList<categoryData> =
//                    DanhMucList.getDanhMucList()
//                        .filter {
//                            it.name.contains(
//                                (p0.toString()),
//                                true
//                            )
//                        } as ArrayList<categoryData>
//                adapter = DanhMucAdapter(foundDanhMuc)
//                recyclerView!!.adapter = adapter
//                recyclerView!!.layoutManager = GridLayoutManager(context, 2)
//            }
//        })
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.i("continue", "view list continue")
//        val text: Editable = autoCompleteTV!!.text
//        autoCompleteTV!!.setText(text)
//        autoCompleteTV!!.setSelection(text.length)
//
//        adapter!!.notifyDataSetChanged()
//        setUpAutoCompleteTVAdapter(DanhMucList.getDanhMucNameList())
//    }
//}