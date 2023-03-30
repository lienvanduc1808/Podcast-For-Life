package com.example.channel

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.channel.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: listOpisodeAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var play_layout: LinearLayout



    var playBtn: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(NgheNgayFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_NgheNgay -> replaceFragment(NgheNgayFragment())
                R.id.action_Profile -> replaceFragment(ProfileFragment())
                R.id.action_ThuVien -> replaceFragment(LibraryFragment())
                R.id.action_Timkiem -> replaceFragment(SearchFragment())
//                R.id.action_Timkiem -> replaceFragment(EpisodeFragment())
                else->{

                }
            }

            true
        }

        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.test_music)
        playBtn = findViewById(R.id.playBtn)
        playBtn?.setOnClickListener{
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
                playBtn?.setImageResource(R.drawable.baseline_pause)
            }
            else{
                mediaPlayer.pause()
                playBtn?.setImageResource(R.drawable.play_arrow)
            }
        }

        //show episode bottom sheet aka screen 6
        var episodeBS = EpisodeBottomSheet()
        play_layout = findViewById(R.id.play_layout)
        play_layout.setOnClickListener {
//            episodeBS.show
            episodeBS.show(getSupportFragmentManager(), "Episode screen")
        }

    }



    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


}