package com.example.channel

import com.example.channel.NgheNgay.albumData
import com.example.channel.NgheNgay.episodeData

data class userData(
    var name: String,
    var address: String,
    var email: String,
    var subcribeChannel: List<String>,
    var downloaded: List<String>,
    var saved: List<String>
)
