package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.content.res.Resources
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
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
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference



class EpisodeBottomSheet : BottomSheetDialogFragment() {
    lateinit var ivCoverImage: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvDescription: TextView
    lateinit var ibPause: ImageButton
    lateinit var ibMore: ImageButton
    lateinit var sbVolume: SeekBar

    lateinit var curTime: TextView
    lateinit var timeRemain: TextView
    lateinit var sbTimebar: SeekBar
    lateinit var ibReplay: ImageButton
    lateinit var ibForward: ImageButton
    lateinit var runnable: Runnable
    private var handler: Handler = Handler()

    private lateinit var audioManager: AudioManager
    private lateinit var idEpisode: String
    private lateinit var idAlbum: String

    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var audioReference: StorageReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.bottomsheet_episode, container, false)

        //for more features option
        ivCoverImage = view.findViewById(R.id.ivCoverImage)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvDescription = view.findViewById(R.id.tvDescription)
        ibPause = view.findViewById(R.id.ibPause)
        ibReplay = view.findViewById(R.id.ibReplay)
        ibForward = view.findViewById(R.id.ibForward)
        sbTimebar = view.findViewById(R.id.sbTimeBar)

        tvDescription.setSingleLine()
        tvDescription.isSelected = true

        ibMore = view.findViewById(R.id.ibMore)

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
            idEpisode = result.getString("idEpisode").toString()
            idAlbum = idEpisode.substring(0, idEpisode.lastIndexOf("ep"))

            tvTitle.text = result.getString("titleEpisode").toString()
            tvDescription.text = result.getString("descriptEpisode").toString()

            ibMore.setOnClickListener {
                val send_data = Bundle().apply {
                    putString("idEpisode", idEpisode)
                    putString("titleEpisode", result.getString("titleEpisode").toString())
                    putString("dateEpisode", result.getString("dateEpisode").toString())
                }
                (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idEpisode2", send_data)
                MoreBottomSheet().show(getParentFragmentManager(), "Bottom Sheet Fragment")
            }

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


            Log.i("aa", idEpisode)
            val episodeAudio = audioReference.child(idEpisode)
            episodeAudio.downloadUrl.addOnSuccessListener { uri ->
                val mediaPlayer = MediaPlayer()
                mediaPlayer.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build())
                mediaPlayer.setDataSource(uri.toString())
                mediaPlayer.prepare()
                mediaPlayer.setOnPreparedListener{
                    sbTimebar.max = mediaPlayer.duration
                }
                sbTimebar.progress = 0
                mediaPlayer.start()

                sbTimebar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                        if(changed){
                            mediaPlayer.seekTo(pos)
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                        // Triển khai xử lý tại đây
                        Log.i("start", "aaaaa")
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                        // Triển khai xử lý tại đây
                        Log.i("stop", "aaaaa")

                    }
                })

                ibReplay.setOnClickListener{
                    Toast.makeText(requireContext(), "Replay", Toast.LENGTH_SHORT).show()
                    if(mediaPlayer.currentPosition > 0){
                        if(mediaPlayer.currentPosition < 15000){
                            sbTimebar.progress = 0
                        }
                        else{
                            sbTimebar.progress = mediaPlayer.currentPosition - 15000
                        }
                    }
                    mediaPlayer.seekTo(sbTimebar.progress)
                }

                ibForward.setOnClickListener{
                    Toast.makeText(requireContext(), "Forward", Toast.LENGTH_SHORT).show()

                    if(mediaPlayer.currentPosition < mediaPlayer.duration){
                        if(mediaPlayer.currentPosition >= (mediaPlayer.duration - 30000)){
                            sbTimebar.progress = mediaPlayer.duration
                        }
                        else{
                            sbTimebar.progress = mediaPlayer.currentPosition + 30000
                        }
                    }
                    mediaPlayer.seekTo(sbTimebar.progress)
                }

                ibPause.setOnClickListener{
                    if(!mediaPlayer.isPlaying){
                        mediaPlayer.start()
                        ibPause.setImageResource(R.drawable.baseline_pause)
                    }
                    else{
                        mediaPlayer.pause()
                        ibPause.setImageResource(R.drawable.play_arrow_40)
                    }
                }

                handler.postDelayed(object: Runnable{
                    override fun run(){
                        try{
                            sbTimebar.progress = mediaPlayer.currentPosition
                            handler.postDelayed(this, 1000)
                        }catch(e:Exception) {
                            sbTimebar.progress=0
                        }
                    }
                }, 0)

                mediaPlayer.setOnCompletionListener {
                    ibPause.setImageResource(R.drawable.play_arrow_40)
                    sbTimebar.progress = 0
                }
            }.addOnFailureListener { exception ->
                // Xử lý khi không lấy được link URL của ảnh
            }
        }
    }
}

