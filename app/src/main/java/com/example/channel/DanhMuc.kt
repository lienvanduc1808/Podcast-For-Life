package com.example.channel
import kotlinx.serialization.Serializable
@Serializable
data class DanhMuc(
    var name: String,
    var image: String,
    var listAlbum: List<Album>
)

