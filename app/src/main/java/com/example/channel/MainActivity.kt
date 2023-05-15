package com.example.channel

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.channel.Library.LibraryFragment
import com.example.channel.Search.DanhMucFragment
import com.example.channel.NgheNgay.EpisodeBottomSheet
import com.example.channel.NgheNgay.HomeFragment
import com.example.channel.NgheNgay.ListOpisodeAdapter
import com.example.channel.Profile.ProfileFragment

import com.example.channel.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class MainActivity : AppCompatActivity(), DataListener {
    private lateinit var listView: ListView
    private lateinit var adapter: ListOpisodeAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var play_layout: LinearLayout

    private var bundle: Bundle? = null
    private var mediaPlayer: MediaPlayer? = null
    private var handler: Handler = Handler()
    private lateinit var storageReference: StorageReference
    private lateinit var audioReference: StorageReference

    private lateinit var ivNowPlaying: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var ibPlay: ImageButton
    private lateinit var ibForward: ImageButton

    private var idEpisode: String = ""
    private var idAlbum: String = ""
    private var isPlay: Boolean? = null
    private var curPosition: Int? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        replaceFragment(HomeFragment())


        storageReference =  FirebaseStorage.getInstance().getReference("Album")
        audioReference =  FirebaseStorage.getInstance().getReference("AudioEpisode")
        ivNowPlaying = findViewById(R.id.ivNowPlaying)
        tvTitle = findViewById(R.id.tvTitle)
        ibPlay = findViewById(R.id.ibPlay)
        ibForward = findViewById(R.id.ibForward)

        //    replaceFragment(SignInFragment())
//        val signInFragment = SignInFragment()
//        val signUpFragment = SignUpFragment()

//        binding.btnSignIn.setOnClickListener {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, signInFragment)
//                .commit()
//        }
//
//        binding.btnSignUp.setOnClickListener {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, signUpFragment)
//                .commit()
//        }
//
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_NgheNgay -> replaceFragment(HomeFragment())
                R.id.action_Profile -> replaceFragment(ProfileFragment())
                R.id.action_ThuVien -> replaceFragment(LibraryFragment())
                R.id.action_Timkiem -> replaceFragment(DanhMucFragment())
                else -> {

                }
            }

            true
        }

        ibPlay?.setOnClickListener {
            if(mediaPlayer != null){
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                    Log.i("stop", "bbbb")

                    ibPlay?.setImageResource(R.drawable.play_arrow)
                } else {
                    mediaPlayer?.start()
                    Log.i("play", "aaa")
                    ibPlay?.setImageResource(R.drawable.baseline_pause)

                }
            }

        }

        ibForward?.setOnClickListener {
            if(mediaPlayer != null){
                Log.i("cur", mediaPlayer!!.currentPosition.toString())
                Log.i("du", mediaPlayer!!.duration.toString())
                if(mediaPlayer!!.currentPosition + 30000 >= mediaPlayer!!.duration){
                    mediaPlayer!!.seekTo(mediaPlayer!!.duration)
                }
                else{
                    mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition + 30000)
                }
            }
        }

        mediaPlayer?.setOnCompletionListener {
            Log.i("isComp", "cccccc")
            ibPlay?.setImageResource(R.drawable.play_arrow)
        }

        //show episode bottom sheet aka screen 6
        var episodeBS = EpisodeBottomSheet()
        play_layout = findViewById(R.id.play_layout)
        play_layout.setOnClickListener {
            if(mediaPlayer != null){
                episodeBS.show(getSupportFragmentManager(), "Episode screen")
//                supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()

//                (context as AppCompatActivity).getSupportFragmentManager().setFragmentResult("send_idEpisode", bundle)
                bundle?.let { it1 -> supportFragmentManager.setFragmentResult("send_idEpisode", it1) }

            }
        }

    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onMediaPlayerRecevied(mediaPlayer: MediaPlayer) {
        this.mediaPlayer = mediaPlayer
        if (mediaPlayer!!.isPlaying) {
            ibPlay?.setImageResource(R.drawable.baseline_pause)
        }
    }
    override fun onDataRecevied(bundle: Bundle?){
        bundle?.let {
            it.getString("idEpisode")?.let { it -> idEpisode = it }
            it.getString("titleEpisode")?.let { it -> tvTitle.text = it }
        }
        this.bundle = bundle
        idAlbum = idEpisode.substring(0, idEpisode.lastIndexOf("ep"))

        storageReference = storageReference.child(idAlbum)
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri)
                .into(ivNowPlaying)
        }.addOnFailureListener { exception ->
            // Handle any errors
            Log.e(
                "FirebaseStorage",
                "Error getting download URL",
                exception
            )
        }



//        val episodeAudio = audioReference.child(idEpisode)
//        episodeAudio.downloadUrl.addOnSuccessListener { uri ->
//            mediaPlayer.setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .build()
//            )
//            mediaPlayer.setDataSource(uri.toString())
//            mediaPlayer.prepare()
//
//        }
    }
}


