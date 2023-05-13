package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.content.res.Resources
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.example.channel.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * A simple [Fragment] subclass.
 * Use the [MoreBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */


class MoreBottomSheet : BottomSheetDialogFragment(){
    private lateinit var ivCoverImage2: ImageView
    private lateinit var tvTitle2: TextView
    private lateinit var tvDate2: TextView
    private lateinit var llGoToAlb2: LinearLayout
    private lateinit var llSave2: LinearLayout
    private lateinit var llDownload2: LinearLayout
    private lateinit var ibClose: ImageButton

    private lateinit var idEpisode: String
    private lateinit var idAlbum: String

    private lateinit var auth: FirebaseAuth
    private lateinit var userReference: DatabaseReference
    private lateinit var storageReference: StorageReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottomsheet_more, container, false)

        ivCoverImage2 = view.findViewById(R.id.ivCoverImage2)

        tvTitle2 = view.findViewById(R.id.tvTitle2)
        tvTitle2.setSingleLine()

        tvDate2 = view.findViewById(R.id.tvDate2)
        tvDate2.setSingleLine()

        llGoToAlb2 = view.findViewById(R.id.llGoToAlb2)
        llSave2 = view.findViewById(R.id.llSave2)
        llDownload2 = view.findViewById(R.id.llDownload2)

        ibClose = view.findViewById(R.id.ibClose)
        ibClose.setOnClickListener {
            dismiss()
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))

        auth = FirebaseAuth.getInstance()
        userReference = FirebaseDatabase.getInstance().getReference("users/"+auth.currentUser?.uid)


        parentFragmentManager.setFragmentResultListener("send_idEpisode2", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@MoreBottomSheet)
            idEpisode = result.getString("idEpisode").toString()
            idAlbum = idEpisode.substring(0, idEpisode.lastIndexOf("ep"))

            storageReference = FirebaseStorage.getInstance().reference.child("Album/$idAlbum")
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(requireContext()).load(uri).into(ivCoverImage2)
            }.addOnFailureListener { exception ->
                // Handle any errors
                Log.e(
                    "FirebaseStorage",
                    "Error getting download URL",
                    exception
                )
            }
            tvTitle2.text = result.getString("titleEpisode").toString()
            tvDate2.text = result.getString("dateEpisode").toString()

            llGoToAlb2.setOnClickListener {
                (context as AppCompatActivity).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, NgheNgayFragment())
                    .addToBackStack(null)
                    .commit()

                val send_data = Bundle().apply {
                    putString("idAlbum", idEpisode)
                    Log.d("idAlbum", idEpisode)

                }
                (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idAlbum", send_data)

                dismiss()
            }
            llSave2.setOnClickListener {
                userReference.get().addOnSuccessListener {
                    if (it.exists()){
                        userReference.child("saved").push().setValue(idEpisode)
                    }
                }
                dismiss()
            }
            llDownload2.setOnClickListener {
                //do here
                dismiss()
            }

        }

    }
}