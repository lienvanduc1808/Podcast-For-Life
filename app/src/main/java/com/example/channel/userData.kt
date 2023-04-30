package com.example.channel

data class userData(
    var name: String,
    var address: String,
    var email: String,
    var sumalbum: Int,
){
    constructor(name: String, address: String, email: String) : this(name, address, email, 0) {
    }
}
