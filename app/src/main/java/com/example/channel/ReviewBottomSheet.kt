package com.example.channel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvPostReview = view.findViewById(R.id.tvPostReview)
        tvPostReview?.setOnClickListener {
            rbRating2 = view.findViewById(R.id.rbRating2)
            etCmt = view.findViewById(R.id.etCmt)

            //do post cmt feature
            dismiss()
        }
    }
}