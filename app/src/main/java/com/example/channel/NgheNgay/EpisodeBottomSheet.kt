package com.example.channel.NgheNgay

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.channel.Profile.MoreBottomSheet
import com.example.channel.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * A simple [Fragment] subclass.
 * Use the [EpisodeBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class EpisodeBottomSheet : BottomSheetDialogFragment() {
    lateinit var tvTitle: TextView
    lateinit var tvDate: TextView
    lateinit var ibPause: ImageButton
    lateinit var ibMore: ImageButton
    lateinit var curTime: TextView
    lateinit var timeRemain: TextView
    lateinit var sbTimebar: SeekBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.bottomsheet_episode, container, false)

        //for more features option
        ibMore = view.findViewById(R.id.ibMore)
        ibMore.setOnClickListener {
            MoreBottomSheet().show(getParentFragmentManager(), "Bottom Sheet Fragment")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get by id
        tvTitle = view.findViewById(R.id.tvTitle)
        tvDate = view.findViewById(R.id.tvDate)
        ibPause = view.findViewById(R.id.ibPause)
        curTime = view.findViewById(R.id.tvCurrentTime)
        timeRemain = view.findViewById(R.id.tvTimeRemaining)
        sbTimebar = view.findViewById(R.id.sbTimeBar)

        //set up auto scroll for text view
//        tvTitle.setSingleLine()
//        tvTitle.isSelected = true

        tvDate.setSingleLine()
        tvDate.isSelected = true
        tvDate.text = "bay ke keo hai doi tuoi tre bay ke keo hai doi tuoi tre bay ke keo hai doi tuoi tre "

        //set up for play button
//        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.test_music)
        var storageReference: StorageReference
        storageReference =  FirebaseStorage.getInstance().getReference("AudioEpisode/zfOmRg42BEYgMyi0P2SyT9fJ4S52alb15ep3")

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build())
            mediaPlayer.setDataSource(uri.toString())
            mediaPlayer.prepare()
            sbTimebar.progress = 0
            sbTimebar.max = mediaPlayer.duration
            mediaPlayer.start()
            ibPause.setOnClickListener{
                if(!mediaPlayer.isPlaying){
                    mediaPlayer.start()
                    ibPause.setImageResource(R.drawable.baseline_pause)
                }
                else{
                    mediaPlayer.pause()
                ibPause.setImageResource(R.drawable.play_arrow_40)
            }
            sbTimebar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                    Log.i("aaa", "mmmmmmmmmmm")

                    if(changed){
                        Log.i("aaa", "mmmmmmmmmmm")
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


            }
        }.addOnFailureListener { exception ->
            // Xử lý khi không lấy được link URL của ảnh
        }


//        ibPause.setOnClickListener{
//            if(!mediaPlayer.isPlaying){
//                mediaPlayer.start()
//                ibPause.setImageResource(R.drawable.baseline_pause)
//            }
//            else{
//                mediaPlayer.pause()
//            ibPause.setImageResource(R.drawable.play_arrow_40)
//            }
//        }
    }
}
