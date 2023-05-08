package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.channel.R
import com.example.channel.Search.reviewData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class AllReviewFragment : Fragment() {

    lateinit var ibBack2: ImageButton
    lateinit var tvBack: TextView
    lateinit var tvMakeReview2: TextView
    lateinit var lvAllReview: ListView

    private lateinit var ref: String
    private val reviews = arrayListOf<reviewData>()

    private lateinit var databaseReference: DatabaseReference
    private lateinit var userReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_all_review, container, false)
        ibBack2 = view.findViewById(R.id.ibBack2)
        ibBack2?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        tvBack = view.findViewById(R.id.tvBack)
        tvBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        lvAllReview = view.findViewById(R.id.lvAllReview)

        tvMakeReview2 = view.findViewById(R.id.tvMakeReview2)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))

        userReference = FirebaseDatabase.getInstance().getReference("users")

        parentFragmentManager.setFragmentResultListener("send_ref", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@AllReviewFragment)
            ref = result.getString("ref").toString()

            databaseReference = FirebaseDatabase.getInstance().getReference(ref)
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(albumSnapshot: DataSnapshot) {
                    for (reviewSnapshot in albumSnapshot.child("reviews").children) {
                        reviews.add(reviewData(
                            findNameUser(reviewSnapshot.child("from").value.toString()),
                            reviewSnapshot.child("rating").value.toString().toFloat(),
                            reviewSnapshot.child("comment").value.toString(),
                            reviewSnapshot.child("date").value.toString()))
                    }

                    Log.d("sz", reviews.size.toString())
                    lvAllReview.adapter = ReviewAdapter4LV(requireContext(), R.layout.album_review, reviews)

                    val send_data = Bundle().apply {
                        putString("ref", ref)
                    }
                    parentFragmentManager.setFragmentResult("send_ref2", send_data)
                }
                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi
                    Toast.makeText(requireContext(), "Can not get data", Toast.LENGTH_SHORT);
                }
            })
        }

        tvMakeReview2?.setOnClickListener {
            ReviewBottomSheet().show(getParentFragmentManager(), "Review screen")
            val send_data = Bundle().apply {
                putString("ref", ref)
            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_ref2", send_data)
        }
    }
    fun findNameUser(idUser: String): String{
        var name = ""
        userReference.child(idUser).get().addOnSuccessListener{
            if (it.exists())
                name = it.child("name").value.toString()
        }
        return name
    }

}