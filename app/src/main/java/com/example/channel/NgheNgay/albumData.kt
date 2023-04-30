package com.example.channel.NgheNgay

data class albumData(
    var album_name: String,
    var channel: String,
    var description: String,
    var logo_album: String,
){
    constructor(name: String, description: String, logo: String) : this(name, "noone", description, logo){}
}