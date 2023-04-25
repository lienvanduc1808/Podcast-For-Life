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
        val items = listOf(
            DanhMuc("Bảng xếp hạng", R.drawable.img_11),
            DanhMuc("Xã hội và văn hóa", R.drawable.img_12),
            DanhMuc("Tin tức", R.drawable.img_13),
            DanhMuc("Hài kịch", R.drawable.img_14),
            DanhMuc("Thể thao", R.drawable.img_15),
            DanhMuc("Kinh doanh", R.drawable.img_16),
            DanhMuc("Tin tức", R.drawable.tinhban),
        DanhMuc("Hài kịch", R.drawable.tinhban),

            // add more items here
        )
        val danhMucNameListForSearch = arrayListOf(
            DanhMuc("Bảng xếp hạng", R.drawable.img_11),
            DanhMuc("Xã hội và văn hóa", R.drawable.img_12),
            DanhMuc("Tin tức", R.drawable.img_13),
            DanhMuc("Hài kịch", R.drawable.img_14),
            DanhMuc("Thể thao", R.drawable.img_15),
            DanhMuc("Kinh doanh", R.drawable.img_16),
            DanhMuc("Tin tức", R.drawable.tinhban),
            DanhMuc("Hài kịch", R.drawable.tinhban),

            )
        DanhMucList.setListData(danhMucNameListForSearch)

        adapter = DanhMucAdapter(items)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = GridLayoutManager( context, 2)
        

//        recyclerView!!.addItemDecoration(
//            DividerItemDecoration( requireContext(),
//                DividerItemDecoration.HORIZONTAL
//            )
//        )
        setUpAutoCompleteTVAdapter(DanhMucList.getDanhMucNameList())

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
                val foundDanhMuc: ArrayList<DanhMuc> =
                    DanhMucList.getDanhMucList()
                        .filter { s ->
                            s.name.contains(
                                (p0.toString()),
                                true
                            )
                        } as ArrayList<DanhMuc>
                adapter = DanhMucAdapter(foundDanhMuc)
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

        adapter!!.notifyDataSetChanged()
        setUpAutoCompleteTVAdapter(DanhMucList.getDanhMucNameList())
    }
}