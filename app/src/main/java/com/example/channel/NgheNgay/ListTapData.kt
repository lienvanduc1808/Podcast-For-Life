package com.example.channel.NgheNgay

class ListTapData(
    val _id: String,
    val date:String,
    val title:String,
    val descript: String,
    val img: String,
    val duration:String,
    val listener: Int,
) {
    constructor(_id: String, date: String, title: String, descript: String, img: String) :
            this(_id, date, title,  descript, img, "00:00", 0){}
    constructor(_id: String, date: String, title: String, descript: String, img: String, duration: String) :
            this(_id, date, title,  descript, img, duration, 0){}
    constructor(_id: String, date: String, title: String, descript: String, img: String, listener: Int) :
            this(_id, date, title,  descript, img, "00:00", listener){}


}