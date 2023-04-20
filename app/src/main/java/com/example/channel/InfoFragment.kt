package com.example.channel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class InfoFragment : Fragment() {
    private lateinit var ivBack5: ImageView
    private lateinit var tvBack5: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_info, container, false)
        ivBack5 = view.findViewById(R.id.ivBack5)
        ivBack5.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        tvBack5 = view.findViewById(R.id.tvBack5)
        tvBack5.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }
}