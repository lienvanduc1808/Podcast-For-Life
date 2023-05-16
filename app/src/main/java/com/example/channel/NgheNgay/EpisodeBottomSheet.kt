package com.example.channel.NgheNgay

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.example.channel.DataListener
import com.example.channel.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.concurrent.TimeUnit


class EpisodeBottomSheet : BottomSheetDialogFragment() {
    private lateinit var mListener: DataListener

    lateinit var ivCoverImage: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvDescription: TextView

    private var bundle: Bundle? = null
    private val mediaPlayer = MediaPlayer()
    lateinit var sbTimebar: SeekBar
    lateinit var tvCurrentTime: TextView
    lateinit var tvTimeRemaining: TextView

    lateinit var ibPause: ImageButton
    lateinit var ibReplay: ImageButton
    lateinit var ibForward: ImageButton

    lateinit var sbVolume: SeekBar

    lateinit var tvSpeed: TextView
    lateinit var ivAirPlay: ImageView
    lateinit var ibMore: ImageButton

    private var handler: Handler = Handler()

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

        mListener = requireActivity() as DataListener

        //for more features option
        ivCoverImage = view.findViewById(R.id.ivCoverImage)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvDescription = view.findViewById(R.id.tvDescription)
        tvCurrentTime = view.findViewById(R.id.tvCurrentTime)
        tvTimeRemaining = view.findViewById(R.id.tvTimeRemaining)
        ibPause = view.findViewById(R.id.ibPause)
        ibReplay = view.findViewById(R.id.ibReplay)
        ibForward = view.findViewById(R.id.ibForward)
        sbTimebar = view.findViewById(R.id.sbTimeBar)
        tvSpeed = view.findViewById(R.id.tvSpeed)
        sbVolume = view.findViewById(R.id.sbVolume)
        ivAirPlay = view.findViewById(R.id.ivAirPlay)
        ivAirPlay.setOnClickListener {
            val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            context?.startActivity(intent)
        }

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
            bundle = result
            Log.i("bundle", bundle!!.getString("idEpisode").toString())
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

                mediaPlayer.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build())
                mediaPlayer.setDataSource(uri.toString())
                mediaPlayer.prepare()
                mediaPlayer.setOnPreparedListener{
                    sbTimebar.max = mediaPlayer.duration
                    val duration = mediaPlayer.duration.toLong()
                    tvTimeRemaining.text = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
                    )
                }
                sbTimebar.progress = 0
                mediaPlayer.playbackParams = PlaybackParams().setSpeed(1F)
                mediaPlayer.start()

                sbVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        // Thiết lập âm lượng media player theo giá trị mới của SeekBar
                        val volume = progress / 100f
                        mediaPlayer.setVolume(volume, volume)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        Log.i("start", "aaaaa")

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        Log.i("stop", "aaaaa")

                    }
                })

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
                        ibPause.setImageResource(R.drawable.baseline_pause_40)
                    }
                    else{
                        mediaPlayer.pause()
                        ibPause.setImageResource(R.drawable.play_arrow_40)
                    }
                }

                tvSpeed.setOnClickListener {
                    if (tvSpeed.text.toString().trim().equals("1x")){
                        tvSpeed.text = "1.5x"
                        mediaPlayer.playbackParams = PlaybackParams().setSpeed(1.5F)
                    }
                    else if (tvSpeed.text.toString().trim().equals("1.5x")){
                        tvSpeed.text = "2x"
                        mediaPlayer.playbackParams = PlaybackParams().setSpeed(2F)
                    }
                    else if (tvSpeed.text.toString().trim().equals("2x")){
                        tvSpeed.text = "1x"
                        mediaPlayer.playbackParams = PlaybackParams().setSpeed(1F)
                    }
                }

                handler.postDelayed(object: Runnable{
                    override fun run(){
                        try{
                            sbTimebar.progress = mediaPlayer.currentPosition
                            handler.postDelayed(this, 1000)
                            val curTime = mediaPlayer.currentPosition.toLong()
                            tvCurrentTime.text = String.format("%02d:%02d",
                                TimeUnit.MILLISECONDS.toMinutes(curTime),
                                TimeUnit.MILLISECONDS.toSeconds(curTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(curTime))
                            );
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mListener.onDataRecevied(bundle)
        mListener.onMediaPlayerRecevied(mediaPlayer)
//        mediaPlayer.release()
    }
}

