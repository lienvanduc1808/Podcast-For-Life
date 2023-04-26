package com.example.channel.Library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.channel.R

class LibraryFragment : Fragment() {
    private lateinit var clSaved: ConstraintLayout
    private lateinit var clDownloaded: ConstraintLayout
    private lateinit var clFollowed: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        clSaved = view.findViewById(R.id.clSaved)
        clDownloaded = view.findViewById(R.id.clDownloaded)
        clFollowed = view.findViewById(R.id.clFollowed)

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }

}