package com.example.podcast_for_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class ThuvienActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thuvien)
        XuLyBottomNavBar()
    }
    private fun XuLyBottomNavBar() {
        val botNavView = findViewById<BottomNavigationView>(R.id.botNavView)
        botNavView.selectedItemId = R.id.action_ThuVien
        botNavView.setOnItemSelectedListener {menuItem ->
            when(menuItem.itemId){
                R.id.action_NgheNgay->{
                    val i1 = Intent(this,MainActivity::class.java)
                    startActivity(i1)
                }
                R.id.action_Profile->{
                    val i2 = Intent(this,Profile::class.java)
                    startActivity(i2)
                }
                R.id.action_ThuVien->{

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