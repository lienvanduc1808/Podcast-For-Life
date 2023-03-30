package com.example.channel

import android.os.Bundle
import androidx.fragment.app.Fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

class EpisodeFragment : Fragment() {
    lateinit var ibMore: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ibMore = view.findViewById(R.id.ibMore)

        var morebs = MoreBottomSheet()

        ibMore.setOnClickListener {
            morebs.show(getParentFragmentManager(), "Bottom Sheet Fragment")
        }

    }
}