package com.example.channel.admin

data class User(
    var name: String,
    var address: String,
    var email: String,
    var sumalbum: Int,
    var idUser: String,
){
    constructor(name: String, address: String, email: String, id: String) : this(name, address, email, 0, id) {
    }
}
