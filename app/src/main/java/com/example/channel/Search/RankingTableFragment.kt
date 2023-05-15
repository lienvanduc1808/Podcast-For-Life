package com.example.channel.Search

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.channel.R
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.channel.NgheNgay.ListTapData
import com.example.channel.Profile.MyPodCastData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RankingTableFragment : Fragment() {
    private lateinit var viewPager: ViewPager2


    private lateinit var auth: FirebaseAuth
    private lateinit var listView: ListView
    private lateinit var adapter2:TopTapAdapter
    private lateinit var authId: String
    private var episodeID:String=""
    private var sumListenerEpisode:Int= 0
    private var albumName:String=""
    private var channelName:String=""
    private var episodeImage:String=""
    private var idAlbum:String=""
    val items = arrayListOf<Top_Item>()
    val itemss = arrayListOf<Top_Item>()
//    val listTopTap = arrayListOf<TopTapData>()
    val listTopEpisode = arrayListOf<ListTapData>()
    val listTopEpisodess = arrayListOf<ListTapData>()
    val listTopEpisodes = arrayListOf<ListTapData>()
    val topEpisodeSort = arrayListOf<ListTapData>()
    val topEpisode = arrayListOf<ListTapData>()
//    val listTopTaps = arrayListOf<TopTapData>()
    private lateinit var adapter: TopItemAdapter
    private lateinit var tvAllAlbum2: TextView
    private lateinit var tvAllAlbum3: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ranking_table, container, false)
        tvAllAlbum2 = view.findViewById(R.id.txtSeeAll)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showList(view)
        turnBack(view)
        showDataAll(view)






    }

    private fun turnBack(view: View) {
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
                if(fragmentManager!=null){
                    fragmentManager?.popBackStack()

                }
            //nhớ phải add fragment này vào backStack thì mới dùng được
            //https://www.youtube.com/watch?v=b9a3-gZ9CGc

        }

    }

    private fun showList(view: View) {
        val txtShowList = view.findViewById<TextView>(R.id.txtShowList)
        val popupMenu = PopupMenu(view?.context, txtShowList)

        popupMenu.inflate(R.menu.popup_danhmuc)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAllCat -> {

                    txtShowList.setText(menuItem.title.toString())
                    showDataAll(view)

                    true
                }
                R.id.menuXH -> {
                    txtShowList.setText(menuItem.title.toString())
                    showDataItemCate(view,"Xã hội và văn hóa")
                    true
                }
                R.id.menuNews -> {
                    txtShowList.setText(menuItem.title.toString())
                    showDataItemCate(view,"Tin tức")
                    true
                }
                R.id.menuComedy -> {
                    txtShowList.setText(menuItem.title.toString())
                    showDataItemCate(view,"Hài")
                    true
                }
                R.id.menuBusiness -> {
                    txtShowList.setText(menuItem.title.toString())
                    showDataItemCate(view,"Kinh doanh")
                    true
                }
                R.id.menuSport -> {
                    txtShowList.setText(menuItem.title.toString())
                    showDataItemCate(view,"Thể thao")
                    true
                }
                else -> false
            }
        }
        txtShowList.setOnClickListener {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)

            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                popupMenu.show()
            }
            true
        }
    }

    private fun showDataAll(view: View) {

        val database = FirebaseDatabase.getInstance()
        val ref = database.reference.child("categories")
        auth = FirebaseAuth.getInstance()
        authId =auth.currentUser?.uid.toString()
        var list = mutableListOf<MyPodCastData>()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (categorySnapshot in dataSnapshot.children) {
//                        val categoryName = categorySnapshot.child("cate_name").value.toString()
                        for (albumSnapshot in categorySnapshot.child("albums").children) {
                            idAlbum =  albumSnapshot.key.toString()
                            albumName = albumSnapshot.child("album_name").value.toString()
                            channelName = albumSnapshot.child("channel").value.toString()
                            for (episodeSnapshot in albumSnapshot.child("episodes").children) {
                                 episodeID = episodeSnapshot.key.toString()
                                 val episodeTitle = episodeSnapshot.child("title").value.toString()
                                 val episodeDes = episodeSnapshot.child("descript").value.toString()
                                 val episodeDate = episodeSnapshot.child("date").value.toString()
                                 episodeImage = episodeSnapshot.child("img").value.toString()
                                 var esposideListener  = episodeSnapshot.child("listener").value

                                var numListener = 0
                                 if(esposideListener !=null){
                                     numListener= esposideListener.toString().toInt()
                                 }else{
                                    numListener =0
                                 }

                                listTopEpisode.add(ListTapData(episodeID, episodeDate, episodeTitle, episodeDes, episodeImage, numListener))
//                                listTopTap.add(TopTapData(episodeImage,episodeName,episodeDate,numListener))
                                sumListenerEpisode+=numListener.toInt()
                            }
                            if(sumListenerEpisode!=0){
                                items.add(Top_Item(albumName,channelName,episodeImage,sumListenerEpisode,idAlbum))
                            }
                            sumListenerEpisode =0
                        }

                    }

                Log.d("hhhh",items.toString())

                var sortlist = items.sortedWith(compareByDescending { it.totalListeners })
                for (album in sortlist) {
                    itemss.add(album)
                }

                adapter = TopItemAdapter(itemss, requireContext())
                adapter.onItemClick = {  album->
                }
                viewPager = view.findViewById(R.id.viewPager)

                viewPager.apply {
                    clipChildren = false  // No clipping the left and right items
                    clipToPadding = false  // Show the viewpager in full width without clipping the padding
                    offscreenPageLimit = 5  // Render the left and right items
                    (getChildAt(0) as RecyclerView).overScrollMode =
                        RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
                }
                viewPager.adapter = adapter
                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))
                viewPager.setPageTransformer(compositePageTransformer)


                var sortlistTopTap = listTopEpisode.sortedWith(compareByDescending { it.listener })
                for (episode in sortlistTopTap ){
                    listTopEpisodes.add(episode)

//                    listTopTaps.add(episode)
                }
                Log.d("listTopEpisodes",listTopEpisodes.toString())

                adapter2 = TopTapAdapter(requireContext(),R.layout.top_tap,listTopEpisodes)
                listView = view.findViewById(R.id.lvRankingTap)
                listView.adapter = adapter2



                tvAllAlbum2.setOnClickListener {
                    replaceFragment(XemTatCaRankingFragment(itemss))
                }





            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })







    }


    private fun showDataItemCate(view: View,cateName:String) {

        val database = FirebaseDatabase.getInstance()
        if(cateName.equals("Thể thao")){
            var ref= database.getReference("categories").child("category_id_2")
            getData(ref)
        }
        if(cateName.equals("Kinh doanh")){
            var ref= database.getReference("categories").child("category_id_4")
            Log.d("ref",ref.toString())
            getData(ref)
        }
        if(cateName.equals("Tin tức")){
            var ref= database.getReference("categories").child("category_id_1")
            Log.d("ref",ref.toString())
            getData(ref)
        }
        if(cateName.equals("Hài")){
            var ref= database.getReference("categories").child("category_id_3")
            Log.d("ref",ref.toString())
            getData(ref)
        }
        if(cateName.equals("Xã hội và văn hóa")){
            var ref= database.getReference("categories").child("category_id_5")
            Log.d("ref",ref.toString())
            getData(ref)
        }



    }

    private fun getData(ref: DatabaseReference) {


        Log.d("co vi","co vo")
        Log.d("ref",ref.toString())
        auth = FirebaseAuth.getInstance()
        authId =auth.currentUser?.uid.toString()
        var list = mutableListOf<MyPodCastData>()
        val itemCate = arrayListOf<Top_Item>()
        val itemCateSort = arrayListOf<Top_Item>()



        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (albumSnapshot in dataSnapshot.child("albums").children) {
                    idAlbum =  albumSnapshot.key.toString()
                    albumName = albumSnapshot.child("album_name").value.toString()
                    channelName = albumSnapshot.child("channel").value.toString()
                    for (episodeSnapshot in albumSnapshot.child("episodes").children) {
                        episodeID = episodeSnapshot.key.toString()
                        val episodeTitle = episodeSnapshot.child("title").value.toString()
                        val episodeDes = episodeSnapshot.child("descript").value.toString()
                        val episodeDate = episodeSnapshot.child("date").value.toString()
                        episodeImage = episodeSnapshot.child("img").value.toString()
                        var esposideListener  = episodeSnapshot.child("listener").value

                        var numListener = 0
                        if(esposideListener !=null){
                            numListener= esposideListener.toString().toInt()
                        }else{
                            numListener =0
                        }

                        listTopEpisodess.add(ListTapData(episodeID, episodeDate, episodeTitle, episodeDes, episodeImage, numListener))
//                                listTopTap.add(TopTapData(episodeImage,episodeName,episodeDate,numListener))
                        sumListenerEpisode+=numListener.toInt()
                    }
                    if(sumListenerEpisode!=0){
                        itemCate.add(Top_Item(albumName,channelName,episodeImage,sumListenerEpisode,idAlbum))
                       // Log.d("hhhh",sumListenerEpisode.toString()+albumName)
                    }
                    sumListenerEpisode =0



                }

                Log.d("listTopEpisodess",listTopEpisodess.toString())


                val sortlist = itemCate.sortedWith(compareByDescending { it.totalListeners })
                for (album in sortlist) {
                    itemCateSort.add(album)


                }
                Log.d("topEpisode",listTopEpisodess.toString())


                var sortTopEpisode = listTopEpisodess.sortedWith(compareByDescending { it.listener })
                for (episode in sortTopEpisode ){
                    topEpisodeSort.add(episode)
                }

                Log.d("topEpisodeSort",topEpisodeSort.toString())



                adapter2 = TopTapAdapter(requireContext(),R.layout.top_tap, topEpisodeSort)
                listView = view!!.findViewById(R.id.lvRankingTap)
                listView.adapter = adapter2

                adapter = TopItemAdapter(itemCateSort, requireContext())
                adapter.onItemClick = {  album->
                }
                viewPager = view!!.findViewById(R.id.viewPager)

                viewPager.apply {
                    clipChildren = false  // No clipping the left and right items
                    clipToPadding = false  // Show the viewpager in full width without clipping the padding
                    offscreenPageLimit = 5  // Render the left and right items
                    (getChildAt(0) as RecyclerView).overScrollMode =
                        RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
                }
                viewPager.adapter = adapter
                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))
                viewPager.setPageTransformer(compositePageTransformer)


                tvAllAlbum2.setOnClickListener {
                    replaceFragment(XemTatCaRankingFragment(itemCateSort))
                }






            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }


}






