package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.channel.R
import com.example.channel.Search.reviewData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewBottomSheet : BottomSheetDialogFragment() {
    lateinit var tvCancelReview: TextView
    lateinit var tvPostReview: TextView
    lateinit var rbRating2: RatingBar
    lateinit var etCmt: EditText

    private lateinit var idCategory: String
    private lateinit var idAlbum: String
    private lateinit var idCmt: String

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.bottomsheet_review, container, false)

        tvCancelReview = view.findViewById(R.id.tvCancelReview)
        tvCancelReview?.setOnClickListener {
            dismiss()
        }
        rbRating2 = view.findViewById(R.id.rbRating2)
        etCmt = view.findViewById(R.id.etCmt)
        tvPostReview = view.findViewById(R.id.tvPostReview)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))

        idCmt = ""
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("categories")

        parentFragmentManager.setFragmentResultListener("sendatafrAllReview2MakeReview", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@ReviewBottomSheet)
            idCategory = result.getString("idCategory").toString()
            idAlbum = result.getString("idAlbum").toString()

            databaseReference.child("$idCategory/albums/$idAlbum").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (reviewSnapshot in snapshot.child("reviews").children) {
                        if (reviewSnapshot.child("from").value.toString().equals(auth.currentUser!!.uid)) {
                            idCmt = reviewSnapshot.key.toString()
                            rbRating2.setRating(reviewSnapshot.child("rating").value.toString().toFloat())
                            etCmt.setText(reviewSnapshot.child("comment").value.toString())
                            val send_data = Bundle().apply {
                                putString("ref", idAlbum)
                            }
                            parentFragmentManager.setFragmentResult("send_ref", send_data)
                            break
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
            tvPostReview?.setOnClickListener {
                databaseReference = databaseReference.child("$idCategory/albums/$idAlbum/reviews")
                if (idCmt == "")
                    idCmt = databaseReference.push().key!!
                databaseReference.child(idCmt).setValue(reviewData(auth.currentUser!!.uid, rbRating2.rating, etCmt.text.toString(), "getDate"))
                //do post cmt feature
                val send_data = Bundle().apply {
                    putString("ref", idAlbum)
                }
                parentFragmentManager.setFragmentResult("send_ref", send_data)
                dismiss()
            }
        }
    }
}