package com.example.channel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListTapFragment : Fragment() {
    private lateinit var ivBack4: ImageView

    private lateinit var listView: ListView
    private lateinit var listTapAdapter:ListTapAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_tap, container, false)
        ivBack4 = view.findViewById(R.id.ivBack4)
        ivBack4?.setOnClickListener {
            parentFragmentManager.popBackStack()
            //nhớ phải add fragment này vào backStack thì mới dùng được
            //https://www.youtube.com/watch?v=b9a3-gZ9CGc

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = mutableListOf<ListTapData>()
        parentFragmentManager.setFragmentResultListener("send_ref", this) { _, result ->

            parentFragmentManager.beginTransaction().show(this@ListTapFragment)
            val taskDanhmuc = result.getString("ref")
            Log.d("taskDanhmuc", taskDanhmuc.toString())
            val albumRef = FirebaseDatabase.getInstance().getReference(taskDanhmuc.toString())
            val episodesRef = albumRef.child("episodes")
            episodesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    for (episodeSnapshot in snapshot.children) {

                        val date = episodeSnapshot.child("date").value.toString()
                        val epdes = episodeSnapshot.child("descript").value.toString()
                        val epTitle = episodeSnapshot.child("title").value.toString()
                        list.add(ListTapData(date,epTitle,"20 phut"))
                        listTapAdapter = ListTapAdapter(requireContext(), R.layout.list_tap, list)
                        listView = view.findViewById(R.id.lvTap)
                        listView.adapter = listTapAdapter

                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })





        }








    }
}