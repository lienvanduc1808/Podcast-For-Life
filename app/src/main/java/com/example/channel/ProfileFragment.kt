package com.example.channel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

class ProfileFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val clMyPodcast = view.findViewById<ConstraintLayout>(R.id.clMyPodcast)
        clMyPodcast.setOnClickListener {
            val fragment = MyPodcastFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)

                .addToBackStack(null)
                .commit()
        }

















    }

}