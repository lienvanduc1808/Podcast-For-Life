package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.channel.*
import com.example.channel.R
import com.example.channel.NgheNgay.reviewData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.values
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.tasks.await

class NgheNgayFragment : Fragment() {
    private lateinit var fragmentContext: Context
    private lateinit var ibBack: ImageButton
    private lateinit var ibSub: ImageButton
    private lateinit var rivAlbLogo: RoundedImageView
    private lateinit var tvAlbName: TextView
    private lateinit var tvAlbChannel: TextView
    private lateinit var tvAlbDescription: TextView
    private lateinit var btnPlayNewest: Button

    private lateinit var tvAllEpisode: TextView
    private lateinit var lvListEpisode: ListView
    private lateinit var listOpisodeAdapter: ListOpisodeAdapter
    private lateinit var reviewAdapter: ReviewAdapter

    private lateinit var tvAllReview: TextView

    private lateinit var tvAverage: TextView
    private lateinit var tvTotalRating: TextView
    private lateinit var pb5start: ProgressBar
    private lateinit var pb4start: ProgressBar
    private lateinit var pb3start: ProgressBar
    private lateinit var pb2start: ProgressBar
    private lateinit var pb1start: ProgressBar

    private lateinit var vpReview: ViewPager2
//    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var tvMakeReview: TextView

    private lateinit var ref: String
    private lateinit var idAlbum: String
    private var name: String = ""
    private val episodes = arrayListOf<ListTapData>()
    private val reviews = arrayListOf<reviewData>()

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_nghe_ngay, container, false)
        view.setTag("NgheNgayFragmentTag")
        ibBack = view.findViewById(R.id.ibBack)
        ibSub = view.findViewById(R.id.addBtn)


        rivAlbLogo = view.findViewById(R.id.rivAlbLogo)
        tvAlbName = view.findViewById(R.id.tvAlbName)
        tvAlbChannel = view.findViewById(R.id.tvAlbChannel)
        tvAlbDescription = view.findViewById(R.id.tvAlbDescription)
        btnPlayNewest = view.findViewById(R.id.btnPlayNewest)

        tvAllEpisode = view.findViewById(R.id.tvAllEpisode)
        lvListEpisode = view.findViewById(R.id.lvListEpisode)

        tvAllReview = view.findViewById(R.id.tvAllReview)
        vpReview = view.findViewById(R.id.vpReview)
        tvMakeReview = view.findViewById(R.id.tvMakeReview)

        tvAverage = view.findViewById(R.id.tvAverage)
        tvTotalRating = view.findViewById(R.id.tvTotalRating)
        pb5start = view.findViewById(R.id.pb5start)
        pb4start = view.findViewById(R.id.pb4start)
        pb3start = view.findViewById(R.id.pb3start)
        pb2start = view.findViewById(R.id.pb2start)
        pb1start = view.findViewById(R.id.pb1start)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userReference = FirebaseDatabase.getInstance().getReference("users")
        auth = FirebaseAuth.getInstance()
        val idUser = auth.currentUser?.uid
        val nodeUser = userReference.child("$idUser")
        parentFragmentManager.setFragmentResultListener("send_idAlbum", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@NgheNgayFragment)
            idAlbum = result.getString("idAlbum").toString()
            nodeUser?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val subUser = snapshot.child("subcribed")
                    if(subUser.exists()){
                        if(subUser.child("${idAlbum}").exists()){
                            ibSub.setImageResource(R.drawable.baseline_check_24)
                        }
                    }

                    ibSub.setOnClickListener {
                        val subAlbum = subUser.child("${idAlbum}")
                        if(subUser.exists()) {
                            if (subAlbum.exists()) {
                                subAlbum.ref.removeValue()
                                ibSub.setImageResource(R.drawable.add)
                            }
                            else {
                                subAlbum.ref.setValue("")
                            }
                        }
                        else{
                            subAlbum.ref.setValue("")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

            databaseReference = FirebaseDatabase.getInstance().getReference("categories")
            databaseReference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    reviews.clear()
                    episodes.clear()
                    for (categorySnapshot in snapshot.children) {
                        for (albumSnapshot in categorySnapshot.child("albums").children) {
                            if (albumSnapshot.key.toString().equals(idAlbum)) {
                                val albref = albumSnapshot.ref.toString()
                                ref = albref.replace("https://testdb-80aa6-default-rtdb.firebaseio.com/","")
                                tvAlbName.setText(albumSnapshot.child("album_name").value.toString())
                                tvAlbChannel.setText(albumSnapshot.child("channel").value.toString())
                                tvAlbDescription.setText(albumSnapshot.child("description").value.toString())
                                storageReference = FirebaseStorage.getInstance().reference.child("Album/$idAlbum")
                                storageReference.downloadUrl.addOnSuccessListener { uri ->
                                    Glide.with(fragmentContext).load(uri).into(rivAlbLogo)
                                }.addOnFailureListener { exception ->
                                    // Handle any errors
                                    Log.e(
                                        "FirebaseStorage",
                                        "Error getting download URL",
                                        exception
                                    )
                                }

                                for (episodeSnapshot in albumSnapshot.child("episodes").children){
                                    val epTitle = episodeSnapshot.child("title").value.toString()
                                    val epdes = episodeSnapshot.child("descript").value.toString()
                                    val date = episodeSnapshot.child("date").value.toString()
                                    val img = episodeSnapshot.child("img").value.toString()
                                    val _id = episodeSnapshot.key.toString()
                                    episodes.add(ListTapData(_id, date, epTitle, epdes, img, ""))
                                }
                                btnPlayNewest.setOnClickListener {
                                    EpisodeBottomSheet().show((context as AppCompatActivity).getSupportFragmentManager(), "Episode screen")

                                    val send_data = Bundle().apply {
                                        putString("idEpisode", episodes.last()._id.toString())
                                        putString("dateEpisode", episodes.last().date.toString())
                                        putString("titleEpisode", episodes.last().title.toString())
                                        putString("descriptEpisode", episodes.last().descript.toString())
                                        putString("imgEpisode", episodes.last().img.toString())
                                    }
                                    (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idEpisode", send_data)

                                }

                                lvListEpisode.adapter = ListOpisodeAdapter(fragmentContext, R.layout.list_opisode, episodes.take(3))
                                tvAllEpisode?.setOnClickListener {
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frame_layout, ListTapFragment()).addToBackStack(null).commit()
                                    val send_data = Bundle().apply {
                                        putString("ref", ref)
                                        putString("ChannelName", albumSnapshot.child("channel").value.toString())
                                    }
                                    (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_ref", send_data)
                                }

                                for (reviewSnapshot in albumSnapshot.child("reviews").children)
                                    reviews.add(reviewData(reviewSnapshot.child("from").value.toString(), reviewSnapshot.child("rating").value.toString().toFloat(),
                                            reviewSnapshot.child("comment").value.toString(), reviewSnapshot.child("date").value.toString()))

                                showRating()
                                vpReview.adapter = ReviewAdapter(reviews)
                                Log.i("viewslist", reviews.toString())
                                tvAllReview?.setOnClickListener {
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.frame_layout, AllReviewFragment()).addToBackStack(null).commit()
                                    val send_data = Bundle().apply {
                                        putString("ref", ref)
                                    }
                                    (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_ref1", send_data)
                                }
                                tvMakeReview?.setOnClickListener {
                                    ReviewBottomSheet().show(getParentFragmentManager(), "Review screen")
                                    val send_data = Bundle().apply {
                                        putString("ref", ref)
                                    }
                                    (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_ref", send_data)
                                }


                                break
                            }
                        }
                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            })
            ibBack.setOnClickListener {


                    val taskDanhmuc = result.getString("tendanhmucs")

                    val send_data = Bundle().apply {
                        putString("tendanhmuc", taskDanhmuc)
                    }
                    (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_dm", send_data)


                parentFragmentManager.popBackStack()

            }
        }
    }

    fun showRating(){
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

            tvAverage.setText(((5*rat5 + 4*rat4 + 3*rat3 + 2*rat2 + rat1).toFloat()/reviews.size).toString().format(1))
            tvTotalRating.setText(reviews.size.toString() + " lượt đánh giá")
        }

    }

    override fun onPause() {
        super.onPause()
        Log.i("pause", "AAAAA")
       // parentFragmentManager.clearFragmentResultListener("send_idAlbum")
    }

    override fun onResume() {
        super.onResume()
        Log.i("resume", "BBBB")

    }
}

