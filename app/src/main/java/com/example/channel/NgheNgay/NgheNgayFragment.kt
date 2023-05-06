package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.channel.*
import com.example.channel.R
import com.example.channel.Search.reviewData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.makeramen.roundedimageview.RoundedImageView

class NgheNgayFragment : Fragment() {
    private lateinit var ibBack: ImageButton

    private lateinit var rivAlbLogo: RoundedImageView
    private lateinit var tvAlbName: TextView
    private lateinit var tvAlbChannel: TextView
    private lateinit var tvAlbDescription: TextView

    private lateinit var tvAllEpisode: TextView
    private lateinit var lvListEpisode: ListView
    private lateinit var listOpisodeAdapter: ListOpisodeAdapter
    private lateinit var reviewAdapter: ReviewAdapter

    private lateinit var tvAllReview: TextView

    private lateinit var pb5start: ProgressBar
    private lateinit var pb4start: ProgressBar
    private lateinit var pb3start: ProgressBar
    private lateinit var pb2start: ProgressBar
    private lateinit var pb1start: ProgressBar

    private lateinit var vpReview: ViewPager2
//    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var tvMakeReview: TextView

    private lateinit var idCategory: String
    private lateinit var idAlbum: String
    private val episodes = arrayListOf<episodeData>()
    private val reviews = arrayListOf<reviewData>()

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var userReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_nghe_ngay, container, false)
        ibBack = view.findViewById(R.id.ibBack)
        ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        rivAlbLogo = view.findViewById(R.id.rivAlbLogo)
        tvAlbName = view.findViewById(R.id.tvAlbName)
        tvAlbChannel = view.findViewById(R.id.tvAlbChannel)
        tvAlbDescription = view.findViewById(R.id.tvAlbDescription)

        tvAllEpisode = view.findViewById(R.id.tvAllEpisode)
        tvAllEpisode?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, ListTapFragment()).addToBackStack(null).commit()
        }
        lvListEpisode = view.findViewById(R.id.lvListEpisode)

        tvAllReview = view.findViewById(R.id.tvAllReview)

        vpReview = view.findViewById(R.id.vpReview)

        tvMakeReview = view.findViewById(R.id.tvMakeReview)
        tvMakeReview?.setOnClickListener {
            ReviewBottomSheet().show(getParentFragmentManager(), "Review screen")
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("send_idAlbum", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@NgheNgayFragment)
            idAlbum = result.getString("idAlbum").toString()

            userReference = FirebaseDatabase.getInstance().getReference("users")
            databaseReference = FirebaseDatabase.getInstance().getReference("categories")
            databaseReference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (categorySnapshot in snapshot.children) {
                        for (albumSnapshot in categorySnapshot.child("albums").children) {
                            if (albumSnapshot.key.toString().equals(idAlbum)) {
                                idCategory = categorySnapshot.key.toString()
                                tvAlbName.setText(albumSnapshot.child("album_name").value.toString())
                                tvAlbChannel.setText(findNameUser(albumSnapshot.child("channel").value.toString()))
                                tvAlbDescription.setText(albumSnapshot.child("description").value.toString())
                                storageReference = FirebaseStorage.getInstance().reference.child("Album/$idAlbum")
                                storageReference.downloadUrl.addOnSuccessListener { uri ->
                                    Glide.with(requireContext()).load(uri).into(rivAlbLogo)
                                }.addOnFailureListener { exception ->
                                    // Handle any errors
                                    Log.e(
                                        "FirebaseStorage",
                                        "Error getting download URL",
                                        exception
                                    )
                                }

                                for (episodeSnapshot in albumSnapshot.child("episodes").children)
                                    episodes.add(episodeData(episodeSnapshot.child("title").value.toString(),
                                        episodeSnapshot.child("descript").value.toString(),
                                        episodeSnapshot.child("date").value.toString(), idAlbum))
                                lvListEpisode.adapter = ListOpisodeAdapter(requireContext(), R.layout.list_opisode, episodes.take(3))

                                for (reviewSnapshot in albumSnapshot.child("reviews").children)
                                    reviews.add(reviewData(
                                        findNameUser(reviewSnapshot.child("from").value.toString()),
                                        reviewSnapshot.child("rating").value.toString().toFloat(),
                                        reviewSnapshot.child("comment").value.toString(),
                                        reviewSnapshot.child("date").value.toString()))
                                vpReview.adapter = ReviewAdapter(reviews)

                                val epiRef = albumSnapshot.ref.toString()
                                val episoRef = epiRef.replace("https://testdb-80aa6-default-rtdb.firebaseio.com/","")
                                val send_data = Bundle().apply {
                                    putString("ref", episoRef)
                                }
                                parentFragmentManager.setFragmentResult("send_ref", send_data)
                                break
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            })

            pb5start = view.findViewById(R.id.pb5start)
            pb5start.setProgress(45)
            pb5start.setMax(100)
            pb4start = view.findViewById(R.id.pb4start)
            pb4start.setProgress(4)
            pb4start.setMax(100)
            pb3start = view.findViewById(R.id.pb3start)
            pb3start.setProgress(0)
            pb3start.setMax(100)
            pb2start = view.findViewById(R.id.pb2start)
            pb2start.setProgress(12)
            pb2start.setMax(100)
            pb1start = view.findViewById(R.id.pb1start)
            pb1start.setProgress(29)
            pb1start.setMax(100)
        }

        tvAllReview?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, AllReviewFragment()).addToBackStack(null).commit()
            val send_data = Bundle().apply {
                putString("idCategory", idCategory)
                putString("idAlbum", idAlbum)
            }
            (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("sendatafrNgheNgay2AllReview", send_data)
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

