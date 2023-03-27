package com.example.podcast_for_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {


    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        XuLyBottomNavBar()






    }

    private fun XuLyBottomNavBar() {
        val botNavView = findViewById<BottomNavigationView>(R.id.botNavView)
        botNavView.selectedItemId = R.id.action_NgheNgay
        botNavView.setOnItemSelectedListener {menuItem ->
            when(menuItem.itemId){
                R.id.action_NgheNgay->{

                }
                R.id.action_Profile->{
                    val i2 = Intent(this,Profile::class.java)
                    startActivity(i2)

                }
                R.id.action_ThuVien->{
                    val i3 = Intent(this,ThuvienActivity::class.java)
                    startActivity(i3)
                }
                R.id.action_Timkiem->{
                    val i4 = Intent(this,TimkiemActivity::class.java)
                    startActivity(i4)
                }
            }
            true
        }

    }
}


