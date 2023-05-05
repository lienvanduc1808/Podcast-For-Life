package com.example.channel.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.channel.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AdminMainActivity : AppCompatActivity() {
    var tabLayout : TabLayout? = null
    var viewPager: ViewPager2? = null
    var viewPagerAdapter: AdminMainAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        viewPagerAdapter = AdminMainAdapter(this)
        viewPager?.adapter = viewPagerAdapter
        val tabLayoutMediator = TabLayoutMediator(tabLayout!!, viewPager!!,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Quản lí danh mục"
                    1 -> tab.text = "Quản lí album"
                    2 -> tab.text = "Quản lí user"
                }
            })

        tabLayoutMediator.attach()



    }
}