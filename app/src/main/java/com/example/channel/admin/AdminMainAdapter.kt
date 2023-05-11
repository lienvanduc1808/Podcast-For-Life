package com.example.channel.admin

import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.channel.admin.*

class AdminMainAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment)
{
    override fun getItemCount(): Int {
        return 3
    }
    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return CateManageFragment()
            1 -> return AlbumManageFragment()
            2 -> return UserManageFragment()
            else -> {
                return Fragment()
            }
        }
    }


}