package com.example.channel

object DanhMucList {
    private var danhMucList = ArrayList<DanhMuc>()
    private var danhMucNameList = ArrayList<String>()


    fun setListData(studentList: ArrayList<DanhMuc>) {
        this.danhMucList = studentList

    fun setListData(danhMucList: ArrayList<DanhMuc>) {
        this.danhMucList =danhMucList

        danhMucNameList.clear()
        for (i in DanhMucList.danhMucList.indices){
            danhMucNameList.add(DanhMucList.danhMucList[i].name)
        }
    }

    fun getDanhMucNameList():ArrayList<String>{
        return this.danhMucNameList
    }

    fun getDanhMucList(): ArrayList<DanhMuc> {
        return this.danhMucList
    }
}
