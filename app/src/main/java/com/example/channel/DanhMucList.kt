package com.example.channel

object DanhMucList {
    private var danhMucList = ArrayList<DanhMuc>()
    private var studentNameList = ArrayList<String>()

    fun setListData(studentList: ArrayList<DanhMuc>) {
        this.danhMucList = studentList
        studentNameList.clear()
        for (i in DanhMucList.danhMucList.indices){
            studentNameList.add(DanhMucList.danhMucList[i].name)
        }
    }

    fun getDanhMucNameList():ArrayList<String>{
        return this.studentNameList
    }

    fun getDanhMucList(): ArrayList<DanhMuc> {
        return this.danhMucList
    }
}
