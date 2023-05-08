package com.example.channel.NgheNgay

import android.content.Context
import android.content.res.Resources
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.example.channel.Profile.MoreBottomSheet
import com.example.channel.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


/**
 * A simple [Fragment] subclass.
 * Use the [EpisodeBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class EpisodeBottomSheet : BottomSheetDialogFragment() {
    lateinit var ivCoverImage: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvDate: TextView
    lateinit var ibPause: ImageButton
    lateinit var ibMore: ImageButton
    lateinit var sbVolume: SeekBar

    private lateinit var audioManager: AudioManager
    private lateinit var ref: String
    private lateinit var idAlbum: String
    private var position: Int = 0

    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var audioReference: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.bottomsheet_episode, container, false)

        //for more features option
        ivCoverImage = view.findViewById(R.id.ivCoverImage)
loi        tvDate = view.findViewById(R.id.tvDate)
        ibPause = view.findViewById(R.id.ibPause)

        tvDate.setSingleLine()
        tvDate.isSelected = true

        sbVolume = view.findViewById(R.id.sbVolume)
        audioManager = getSystemService<AudioManager>(Context.AUDIO_SERVICE)!! //
        sbVolume.setMax(
            audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        )
        sbVolume.setProgress(
            audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC)
        )
        sbVolume.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(arg0: SeekBar) {}
            override fun onStartTrackingTouch(arg0: SeekBar) {}
            override fun onProgressChanged(arg0: SeekBar, progress: Int, arg2: Boolean) {
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    progress, 0
                )
            }
        })

        ibMore = view.findViewById(R.id.ibMore)
        ibMore.setOnClickListener {
            MoreBottomSheet().show(getParentFragmentManager(), "Bottom Sheet Fragment")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))

        databaseReference = FirebaseDatabase.getInstance().getReference("categories")
        storageReference =  FirebaseStorage.getInstance().getReference("Album")
        audioReference =  FirebaseStorage.getInstance().getReference("AudioEpisode")

        parentFragmentManager.setFragmentResultListener("send_idEpisode", this) { _, result ->
            parentFragmentManager.beginTransaction().show(this@EpisodeBottomSheet)
            idAlbum = result.getString("idAlbum").toString()
            position = result.getString("position").toString().toInt()
            databaseReference = FirebaseDatabase.getInstance().getReference("categories")
            databaseReference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (categorySnapshot in snapshot.children) {
                        for (albumSnapshot in categorySnapshot.child("albums").children) {
                            Log.d("posi", position.toString())
                            if (albumSnapshot.key.toString().equals(idAlbum)) {
                                var i: Int = 0
                                for (episodeSnapshot in albumSnapshot.child("episodes").children){
                                    if (i == position) {
                                        Log.d("in","ined")
                                        storageReference = storageReference.child(idAlbum)
                                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                                            Glide.with(requireContext()).load(uri)
                                                .into(ivCoverImage)
                                        }.addOnFailureListener { exception ->
                                            // Handle any errors
                                            Log.e(
                                                "FirebaseStorage",
                                                "Error getting download URL",
                                                exception
                                            )
                                        }
                                        tvTitle.text = episodeSnapshot.child("title").value.toString()
                                        tvDate.text = episodeSnapshot.child("date").value.toString()
                                        audioReference = audioReference.child(episodeSnapshot.key.toString())
                                        break
                                    }
                                    i += 1
                                }
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            })

        }
//        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.test_music)
        ibPause.setOnClickListener{
//            if(!mediaPlayer.isPlaying){
//                mediaPlayer.start()
//                ibPause.setImageResource(R.drawable.baseline_pause)
//            }
//            else{
//                mediaPlayer.pause()
            ibPause.setImageResource(R.drawable.play_arrow_40)
//            }
        }

    }
}