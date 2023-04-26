package com.example.channel.NgheNgay

object DanhMucList {
    private var danhMucList = ArrayList<categoryData>()
    private var danhMucNameList = ArrayList<String>()


    fun setListData(danhMucList: ArrayList<categoryData>) {
        DanhMucList.danhMucList = danhMucList

        danhMucNameList.clear()
        for (i in DanhMucList.danhMucList.indices) {
            danhMucNameList.add(DanhMucList.danhMucList[i].name)
        }
    }


    fun getDanhMucNameList(): ArrayList<String> {
        return danhMucNameList
    }

    fun getDanhMucList(): ArrayList<categoryData> {
        return danhMucList
    }
}






