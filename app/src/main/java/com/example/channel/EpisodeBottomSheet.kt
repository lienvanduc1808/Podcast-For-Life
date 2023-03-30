package com.example.channel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


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
        return inflater.inflate(R.layout.bottomsheet_episode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get by id
        tvTitle = view.findViewById(R.id.tvTitle)
        tvDate = view.findViewById(R.id.tvDate)
        ibPause = view.findViewById(R.id.ibPause)
        ibMore = view.findViewById(R.id.ibMore)

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


        //for more features option
        var morebs = MoreBottomSheet()
        ibMore.setOnClickListener {
            morebs.show(getParentFragmentManager(), "Bottom Sheet Fragment")
        }

    }
}