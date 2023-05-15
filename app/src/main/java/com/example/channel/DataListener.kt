package com.example.channel

import android.media.MediaPlayer
import android.os.Bundle

interface DataListener {
    fun onDataRecevied(bundle: Bundle?)
    fun onMediaPlayerRecevied(mediaPlayer: MediaPlayer)
}