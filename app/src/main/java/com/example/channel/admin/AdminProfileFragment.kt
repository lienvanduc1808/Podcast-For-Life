package com.example.channel.admin
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.channel.Profile.InfoFragment
import com.example.channel.Profile.MyPodcastFragment
import com.example.channel.Profile.NotiFragment
import com.example.channel.R
import com.example.channel.SignInActivity

class AdminProfileFragment : Fragment() {

    private lateinit var clLogout: ConstraintLayout
    private lateinit var clNotification: ConstraintLayout
    private lateinit var adminChart: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_profile, container, false)
        clNotification = view.findViewById(R.id.adminNotification)
        clNotification.setOnClickListener {
            replaceFragment(PushNotificationFragment())
        }
        clLogout = view.findViewById(R.id.adminLogout)
        clLogout.setOnClickListener {
            val intent = Intent(requireContext(),SignInActivity::class.java)
            startActivity(intent)

        }
        adminChart = view.findViewById(R.id.adminChart)
        adminChart.setOnClickListener {
            replaceFragment(ChartFragment())
        }


        return view
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.admin_frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }
}