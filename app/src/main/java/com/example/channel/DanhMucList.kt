package com.example.channel

object DanhMucList {
    private var danhMucList = ArrayList<DanhMuc>()
    private var danhMucNameList = ArrayList<String>()

    fun setListData(danhMucList: ArrayList<DanhMuc>) {
        this.danhMucList =danhMucList
        danhMucNameList.clear()
        for (i in danhMucList.indices){
            danhMucNameList.add(danhMucList[i].name)
        }
    }

    fun getDanhMucNameList():ArrayList<String>{
        return this.danhMucNameList
    }

    fun getDanhMucList(): ArrayList<DanhMuc> {
        return this.danhMucList
    }
}
