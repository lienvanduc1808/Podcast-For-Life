package com.example.channel

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class LibraryFragment : Fragment() {
    private lateinit var clSaved: ConstraintLayout
    private lateinit var clDownloaded: ConstraintLayout
    private lateinit var clFollowed: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)

        // Inflate the layout for this fragment
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clSaved = view.findViewById<ConstraintLayout>(R.id.clSaved)
        clDownloaded = view.findViewById<ConstraintLayout>(R.id.clDownloaded)
        clFollowed = view.findViewById<ConstraintLayout>(R.id.clFollowed)

//        clSaved.setOnClickListener {
//            val subFragment = SavedFragment()
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.frame_layout, subFragment)
//                .addToBackStack(null)
//                .commit()
//        }

        clSaved.setOnClickListener {
            replaceFragment(SavedFragment())
        }

        clDownloaded.setOnClickListener {
            replaceFragment(DownloadedFragment())
        }

        clFollowed.setOnClickListener {
            replaceFragment(FollowedFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clSaved = view.findViewById<ConstraintLayout>(R.id.clSaved)
        clDownloaded = view.findViewById<ConstraintLayout>(R.id.clDownloaded)
        clFollowed = view.findViewById<ConstraintLayout>(R.id.clFollowed)

        clSaved.setOnClickListener {
            val subFragment = SavedFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, subFragment)
                .addToBackStack(null)
                .commit()
        }

        clDownloaded.setOnClickListener {
            val subFragment = DownloadedFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, subFragment)
                .addToBackStack(null)
                .commit()
        }

        clFollowed.setOnClickListener {
            val subFragment = FollowedFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, subFragment)
                .addToBackStack(null)
                .commit()
        }



    }

}