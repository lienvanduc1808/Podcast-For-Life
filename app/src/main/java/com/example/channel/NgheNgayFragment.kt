package com.example.channel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.makeramen.roundedimageview.RoundedImageView


class NgheNgayFragment : Fragment() {
    private lateinit var ibBack: ImageButton
    private lateinit var databaseReference: DatabaseReference

    private lateinit var tvAllEpisode: TextView
    private lateinit var ivLogoAlbum: RoundedImageView
    private lateinit var txtAlbumName: TextView
    private lateinit var txtChannel: TextView
    private lateinit var txtdes: TextView
    private lateinit var txtdm: TextView
    private lateinit var lvListEpisode: ListView
    private lateinit var listOpisodeAdapter: ListOpisodeAdapter

    private lateinit var tvAllReview: TextView


    private lateinit var pb5start: ProgressBar
    private lateinit var pb4start: ProgressBar
    private lateinit var pb3start: ProgressBar
    private lateinit var pb2start: ProgressBar
    private lateinit var pb1start: ProgressBar

    private lateinit var vpReview: ViewPager2
//    private lateinit var reviewAdapter: ReviewAdapter

    private lateinit var tvMakeReview: TextView
    val items = arrayListOf<testData>()

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

        ivLogoAlbum = view.findViewById(R.id.ivLogoAlbum)
        txtAlbumName = view.findViewById(R.id.txtAlbumName)
        txtChannel = view.findViewById(R.id.txtChannel)
        txtdes = view.findViewById(R.id.txtdes)
        txtdm = view.findViewById(R.id.txtdm)


        tvAllEpisode = view.findViewById(R.id.tvAllEpisode)
        tvAllEpisode?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, ListTapFragment()).addToBackStack(null).commit()
        }

        tvAllReview = view.findViewById(R.id.tvAllReview)
        tvAllReview?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, AllReviewFragment()).addToBackStack(null).commit()
        }

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
            val taskDanhmuc = result.getString("idAlbum")
            Log.d("taskDanhmuc", taskDanhmuc.toString())


            val database = FirebaseDatabase.getInstance()
            val ref = database.reference.child("categories")
            val storageRef = FirebaseStorage.getInstance().reference


            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (categorySnapshot in dataSnapshot.children) {
                        for (albumSnapshot in categorySnapshot.child("albums").children) {
                            if (albumSnapshot.key.toString().equals(taskDanhmuc)) {
                                Log.d("cone", "cone")
                                val dmName = categorySnapshot.child("cate_name").value.toString()
                                txtdm.setText(dmName)
                                val albumName = albumSnapshot.child("album_name").value.toString()
                                txtAlbumName.setText(albumName)
                                val imgString = albumSnapshot.child("logo_album").value.toString()
                                val imageRef = storageRef.child("Album/$imgString")
                                imageRef.downloadUrl.addOnSuccessListener { uri ->
                                    // Use the URL to display the image
                                    Glide.with(requireContext()).load(uri).placeholder(R.drawable.img_17).into(ivLogoAlbum)
                                }.addOnFailureListener { exception ->
                                    // Handle any errors
                                    Log.e(
                                        "FirebaseStorage",
                                        "Error getting download URL",
                                        exception
                                    )
                                }
                                val channel = albumSnapshot.child("channel").value.toString()
                                txtChannel.setText(channel.toString())
                                val des = albumSnapshot.child("description").value.toString()
                                txtdes.setText(des.toString())
                                Log.d("datee", "hihi")
                                for (episodeSnapshot in albumSnapshot.child("episodes").children) {
                                    val date = episodeSnapshot.child("date").value.toString()

                                    val epdes = episodeSnapshot.child("descript").value.toString()
                                    val epTitle = episodeSnapshot.child("title").value.toString()
                                    items.add(testData(date,epTitle,epdes))




                                }

                                val itemList = items.take(3)
                                listOpisodeAdapter = ListOpisodeAdapter(
                                    requireContext(),
                                    R.layout.list_opisode,
                                    itemList
                                )
                                lvListEpisode = view.findViewById(R.id.lvListEpisode)
                                lvListEpisode.adapter = listOpisodeAdapter

                                val epiRef = albumSnapshot.ref.toString()
                                val episoRef = epiRef.replace("https://testdb-80aa6-default-rtdb.firebaseio.com/","")


                                val send_data = Bundle().apply {
                                    putString("ref", episoRef)
                                    Log.d("ref",episoRef)

                                }
                               parentFragmentManager.setFragmentResult("send_ref", send_data)






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

            val exReview = arrayListOf(
                Review("Album 1", "user 1", 3, "ngonngonngonngonngon", "12/12/2012"),
                Review("Album 1", "user 1", 4, "ngonngononngon", "12/12/2012"),
                Review("Album 1", "user 1", 2, "ngngonngonngonon", "12/12/2012"),
                Review("Album 1", "user 1", 1, "ngngonngonon", "12/12/2012"),
                Review("Album 1", "user 1", 4, "ngngonngonon", "12/12/2012"),
                Review("Album 1", "user 1", 5, "ngngonngonngonngonon", "12/12/2012"),
                Review("Album 1", "user 1", 2, "ngngonngonon", "12/12/2012")
            )

//        reviewAdapter = ReviewAdapter(exReview)
            vpReview = view.findViewById(R.id.vpReview)
            vpReview.adapter = ReviewAdapter(exReview)

        }


    }
}