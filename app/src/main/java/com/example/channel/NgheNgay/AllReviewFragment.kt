package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.channel.R
import com.example.channel.NgheNgay.reviewData
import com.google.firebase.database.*


class AllReviewFragment : Fragment() {
    private lateinit var fragmentContext: Context

    lateinit var ibBack2: ImageButton
    lateinit var tvBack: TextView
    lateinit var tvMakeReview2: TextView
    lateinit var lvAllReview: ListView

    private lateinit var tvAverage: TextView
    private lateinit var tvTotalRating: TextView
    private lateinit var pb5start: ProgressBar
    private lateinit var pb4start: ProgressBar
    private lateinit var pb3start: ProgressBar
    private lateinit var pb2start: ProgressBar
    private lateinit var pb1start: ProgressBar

    private lateinit var ref: String
    private val reviews = arrayListOf<reviewData>()

    private lateinit var databaseReference: DatabaseReference
    private lateinit var userReference: DatabaseReference

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_all_review, container, false)
        ibBack2 = view.findViewById(R.id.ibBack2)

        tvBack = view.findViewById(R.id.tvBack)

        lvAllReview = view.findViewById(R.id.lvAllReview)

        tvAverage = view.findViewById(R.id.tvAverage)
        tvTotalRating = view.findViewById(R.id.tvTotalRating)
        pb5start = view.findViewById(R.id.pb5start)
        pb4start = view.findViewById(R.id.pb4start)
        pb3start = view.findViewById(R.id.pb3start)
        pb2start = view.findViewById(R.id.pb2start)
        pb1start = view.findViewById(R.id.pb1start)
        tvMakeReview2 = view.findViewById(R.id.tvMakeReview2)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))

        userReference = FirebaseDatabase.getInstance().getReference("users")

        parentFragmentManager.setFragmentResultListener("send_ref1", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@AllReviewFragment)
            ref = result.getString("ref").toString()
            databaseReference = FirebaseDatabase.getInstance().getReference(ref)
            val idAlbum = databaseReference.key.toString()

            ibBack2?.setOnClickListener {
                val send_data = Bundle().apply {
                    putString("idAlbum", idAlbum)
                }
                (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)
                parentFragmentManager.popBackStack()
            }

            tvBack?.setOnClickListener {
                val send_data = Bundle().apply {
                    putString("idAlbum", idAlbum)
                }
                (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)
                parentFragmentManager.popBackStack()
            }
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(albumSnapshot: DataSnapshot) {
                    reviews.clear()
                    for (reviewSnapshot in albumSnapshot.child("reviews").children) {
                        reviews.add(reviewData(
                            reviewSnapshot.child("from").value.toString(),
                            reviewSnapshot.child("rating").value.toString().toFloat(),
                            reviewSnapshot.child("comment").value.toString(),
                            reviewSnapshot.child("date").value.toString()))
                    }
                    showRating()
                    lvAllReview.adapter = ReviewAdapter4LV( fragmentContext, R.layout.album_review, reviews)
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
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_ref", send_data)
        }
    }

    fun showRating(){
        Log.i("size", reviews.size.toString())
        if (reviews.size == 0){
            tvAverage.setText("5")
            tvTotalRating.setText("Hãy là người đầu tiên đánh giá")

        }
        else{
            var rat5 = 0
            var rat4 = 0
            var rat3 = 0
            var rat2 = 0
            var rat1 = 0
            for (rv in reviews) {
                if (rv.rating == 5F) rat5 += 1
                if (rv.rating == 4F) rat4 += 1
                if (rv.rating == 3F) rat3 += 1
                if (rv.rating == 2F) rat2 += 1
                if (rv.rating == 1F) rat1 += 1
            }
            pb5start.progress = (rat5 * 100 / reviews.size).toInt()
            pb4start.progress = (rat4 * 100 / reviews.size).toInt()
            pb3start.progress = (rat3 * 100 / reviews.size).toInt()
            pb2start.progress = (rat2 * 100 / reviews.size).toInt()
            pb1start.progress = (rat1 * 100 / reviews.size).toInt()
//            }
            tvAverage.setText(((5*rat5 + 4*rat4 + 3*rat3 + 2*rat2 + rat1).toFloat()/reviews.size).toString().format(1))
            tvTotalRating.setText(reviews.size.toString() + " lượt đánh giá")
        }
    }


}