package com.example.channel.Profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.channel.R
import com.example.channel.SignInActivity

class ProfileFragment : Fragment() {
    private lateinit var clPersonalInfo: ConstraintLayout
    private lateinit var clMyPodcast: ConstraintLayout
    private lateinit var clNotification: ConstraintLayout
    private lateinit var clLogout: ConstraintLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        clPersonalInfo = view.findViewById(R.id.clPersonalInfo)
        clPersonalInfo.setOnClickListener {
            replaceFragment(InfoFragment())
        }

        clMyPodcast = view.findViewById(R.id.clMyPodcast)
        clMyPodcast.setOnClickListener {
            replaceFragment(MyPodcastFragment())
        }

        clNotification = view.findViewById(R.id.clNotification)
        clNotification.setOnClickListener {
            replaceFragment(NotiFragment())
        }
        clLogout = view.findViewById(R.id.clLogout)
        clLogout.setOnClickListener {
            val intent = Intent(requireContext(),SignInActivity::class.java)
            startActivity(intent)

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val clMyPodcast = view.findViewById<ConstraintLayout>(R.id.clMyPodcast)
//        clMyPodcast.setOnClickListener {
////            val fragment = MyPodcastFragment()
////            parentFragmentManager.beginTransaction()
////                .replace(R.id.frame_layout, fragment)
////
////                .addToBackStack(null)
////                .commit()
//            replaceFragment(MyPodcastFragment())
//        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }
}