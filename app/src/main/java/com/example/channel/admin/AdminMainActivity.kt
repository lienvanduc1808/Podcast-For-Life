package com.example.channel.admin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.Fragment
import com.example.channel.*
import com.example.channel.databinding.ActivityAdminMainBinding


class AdminMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(CateManageFragment())

        binding.adminBottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_ql_cate -> replaceFragment(CateManageFragment())
                R.id.action_ql_album -> replaceFragment(AlbumManageFragment())
                R.id.action_ql_user -> replaceFragment(UserManageFragment())
                R.id.action_admin_profile -> replaceFragment(AdminProfileFragment())
                else->{

                }
            }

            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.admin_frame_layout, fragment)
        fragmentTransaction.commit()
    }

}
