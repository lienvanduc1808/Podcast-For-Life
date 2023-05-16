package com.example.channel.admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.channel.R

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserManageFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adapter: UserManageAdapter? = null
    private lateinit var fragmentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_admin_user_manage, container, false)
        recyclerView = view.findViewById(R.id.user_recycler_view)

        recyclerView?.layoutManager = LinearLayoutManager(fragmentContext)
        val items = arrayListOf<User>()

        val database = Firebase.database
        val reference = database.getReference("users")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter?.clearData()
                for (userSnapshot in snapshot.children) {
                    val id = userSnapshot.key.toString()
                    val userName = userSnapshot.child("name").value as String

                    val useEmail = userSnapshot.child("email").value as String
                    val user = User(userName,"", useEmail,0, id)
                    items.add(user)
                }
                Log.d("calog", "The value of search is: $items")
                adapter = UserManageAdapter(items, fragmentContext)

                recyclerView!!.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi
                Toast.makeText(fragmentContext, "Can not get data", Toast.LENGTH_SHORT);
            }
        })

        return view
    }

}
