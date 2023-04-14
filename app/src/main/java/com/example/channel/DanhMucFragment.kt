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
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = view.findViewById(R.id.rvListDanhMuc)
        autoCompleteTV = view.findViewById(R.id.search_view)
        val items = listOf(
            DanhMuc("Text 1.1", R.drawable.tinhban),
            DanhMuc("Text 1.2", R.drawable.tinhban),
            DanhMuc("Text 1.3", R.drawable.tinhban),
            DanhMuc("Text 1.4", R.drawable.tinhban),
            DanhMuc("Text 1.5", R.drawable.tinhban),
            DanhMuc("Text 1.6", R.drawable.tinhban),
            DanhMuc("Text 1.7", R.drawable.tinhban),
            DanhMuc("Text 1.8", R.drawable.tinhban),

            // add more items here
        )
        adapter = DanhMucAdapter(items)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = GridLayoutManager(context, 2)
        //val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)

//        recyclerView.apply {
//            layoutManager = GridLayoutManager(context, 2)
//            addItemDecoration(object : RecyclerView.ItemDecoration() {
//                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//                    outRect.bottom = spacingInPixels
//                }
//            })
//        }
        setUpAutoCompleteTVAdapter(DanhMucList.getDanhMucNameList())

        return view
    }

    fun setUpAutoCompleteTVAdapter(data: ArrayList<String>) {
        autoCompleteTVAdapter = ArrayAdapter<String>(requireContext(),
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
                val foundStudents: ArrayList<DanhMuc> =
                    DanhMucList.getDanhMucList()
                        .filter { s ->
                            s.name.contains(
                                (p0.toString()),
                                true
                            )
                        } as ArrayList<DanhMuc>
                adapter = DanhMucAdapter(foundStudents)
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