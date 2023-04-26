package com.example.channel.NgheNgay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.channel.Profile.MoreBottomSheet
import com.example.channel.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

        //set up auto scroll for text view
//        tvTitle.setSingleLine()
//        tvTitle.isSelected = true

        tvDate.setSingleLine()
        tvDate.isSelected = true
        tvDate.text = "bay ke keo hai doi tuoi tre bay ke keo hai doi tuoi tre bay ke keo hai doi tuoi tre "

        //set up for play button
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