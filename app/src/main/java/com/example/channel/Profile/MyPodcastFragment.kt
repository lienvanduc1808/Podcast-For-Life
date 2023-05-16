package com.example.channel.Profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.net.toUri
import com.example.channel.Profile.AddNewPodcastFragment

import com.example.channel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class MyPodcastFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var adapter: MyPodcastAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String
    private var episodeID:String=""

    private lateinit var fragmentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_podcast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val database = FirebaseDatabase.getInstance()
        val ref = database.reference.child("categories")
        auth = FirebaseAuth.getInstance()
        authId =auth.currentUser?.uid.toString()
        var list = mutableListOf<MyPodCastData>()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                if(dataSnapshot.children ==null){
                    for (categorySnapshot in dataSnapshot.children) {
                        for (albumSnapshot in categorySnapshot.child("albums").children) {
                            val LogoImage = albumSnapshot.child("logo_album").value.toString()
                            Log.d("logoImage",LogoImage)
                            if (LogoImage.contains(authId)) {
                                val categoryName = categorySnapshot.child("cate_name").value.toString()
                                Log.d("categoryName",categoryName)
                                val albumName = albumSnapshot.child("album_name").value.toString()
                                Log.d("albumName",albumName)
                                for(episodeSnapShot in categorySnapshot.child("albums").child("episodes").children){
                                    episodeID = episodeSnapShot.key.toString()
                                    val episodeDate = episodeSnapShot.child("date").value.toString()
                                    Log.d("episodeDate",episodeDate)


                                    val episodeDes = episodeSnapShot.child("descript").value.toString()
                                    Log.d("episodeDes",episodeDes)

                                    val episodeImage = episodeSnapShot.child("img").value.toString()
                                    Log.d("episodeImage",episodeImage)

                                    val episodeName = episodeSnapShot.child("title").value.toString()
                                    Log.d("episodeName",episodeName)
                                    list.add(MyPodCastData(LogoImage.toUri(),categoryName,albumName,episodeName,episodeDes,episodeID))

                                }













                            }
                            for (episodeSnapshot in albumSnapshot.child("episodes").children) {


                            }
                        }
                    }
                }
                else{
                    for (categorySnapshot in dataSnapshot.children) {
                        for (albumSnapshot in categorySnapshot.child("albums").children) {
                            for (episodeSnapshot in albumSnapshot.child("episodes").children) {
                                val episodeAudio = episodeSnapshot.child("img").value.toString()
                                if (episodeAudio.contains(authId)) {

                                    episodeID = episodeSnapshot.key.toString()
                                    val categoryName = categorySnapshot.child("cate_name").value.toString()

                                    val albumName = albumSnapshot.child("album_name").value.toString()

                                    val episodeDate = episodeSnapshot.child("date").value.toString()


                                    val episodeDes = episodeSnapshot.child("descript").value.toString()

                                    val episodeImage = episodeSnapshot.child("img").value.toString()



                                    val episodeName = episodeSnapshot.child("title").value.toString()


                                    list.add(MyPodCastData(episodeImage.toUri(),categoryName,albumName,episodeName,episodeDes,episodeID))



                                }
                            }
                        }
                    }
                }


                adapter = MyPodcastAdapter(fragmentContext, R.layout.my_podcast_item, list)
                listView = view.findViewById(R.id.lvMyPodcast)

                listView.adapter = adapter
                val listener = object : AdapterView.OnItemClickListener {
                    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        val fragment = EditMyPodcastFragment()
                        parentFragmentManager.beginTransaction()
                            .add(R.id.frame_layout, fragment)
                            .addToBackStack(null)
                            .hide(this@MyPodcastFragment)
                            .commit()
                        val send_data = Bundle().apply {
                            putString("edt_danhmuc",list[position].danh_muc )
                            putString("edt_tenalbum",list[position].ten_album )
                            putString("edt_tentap",list[position].ten_tap)
                            putString("edt_mota",list[position].mo_ta )
                            putString("edt_uri",list[position].uri_podcast )
                            putString("edt_img",list[position].img_podcast.toString() )
                        }
                        parentFragmentManager.setFragmentResult("send_item", send_data)


                    }
                }
                listView.setOnItemClickListener(listener)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })













        parentFragmentManager.setFragmentResultListener("xong", this) { _, result ->

            parentFragmentManager.beginTransaction().show(this@MyPodcastFragment)
//            val taskAlbumName = result.getString("task_tenAlbum")
//            val taskTenTap = result.getString("task_tenTap")
//            val taskDescription = result.getString("task_description")
//            val taskUri = result.getString("task_uri")
//            val taskDanhMuc = result.getString("task_danhmuc")
//            val taskAudio = result.getString("task_audio")
//
//            list.add(MyPodCastData(taskUri!!.toInt(),taskDanhMuc.toString(),taskAlbumName.toString(),taskTenTap.toString(),taskDescription.toString(),taskAudio.toString()))




        }




        //quay lại
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            if(fragmentManager!=null){
                fragmentManager?.popBackStack()

            }
            //nhớ phải add fragment này vào backStack thì mới dùng được
            //https://www.youtube.com/watch?v=b9a3-gZ9CGc

        }

        val txtTaoMoi = view.findViewById<TextView>(R.id.txtTaoMoi)
        txtTaoMoi.setOnClickListener {
            val fragment =
                AddNewPodcastFragment()
            parentFragmentManager.beginTransaction()
                .add(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .hide(this@MyPodcastFragment)
                .commit()



        }


    }




}