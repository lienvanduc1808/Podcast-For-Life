package com.example.channel

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

class RankingTableFragment : Fragment() {
    private lateinit var viewPager: ViewPager2

    private lateinit var adapter: TopItemAdapter

    private lateinit var listView: ListView
    private lateinit var adapter2:TopTapAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking_table, container, false)
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
//                if(fragmentManager!=null){
//                    fragmentManager?.popBackStack()
//
//                }
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
                    showDataXH(view)
                    true
                }
                R.id.menuNews -> {
                    txtShowList.setText(menuItem.title.toString())
                    showDataNews(view)
                    true
                }
                R.id.menuComedy -> {
                    txtShowList.setText(menuItem.title.toString())
                    showDataComedy(view)
                    true
                }
                R.id.menuBusiness -> {
                    txtShowList.setText(menuItem.title.toString())
                    showDataBusiness(view)
                    true
                }
                R.id.menuSport -> {
                    txtShowList.setText(menuItem.title.toString())
                    showDataSport(view)
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
        val items = arrayListOf(
            Top_Item("Title 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("Title 2", "Subtitle 2", R.drawable.trikycamxuc,2.toString()),
            Top_Item("Title 3", "Subtitle 3",R.drawable.trikycamxuc,3.toString() ),
            Top_Item("Title 4", "Subtitle 4", R.drawable.trikycamxuc,4.toString()),
            Top_Item("Title 5", "Subtitle 5", R.drawable.trikycamxuc,5.toString()),
            Top_Item("Title 6", "Subtitle 6",R.drawable.trikycamxuc,6.toString() )
        )
        adapter = TopItemAdapter(items)
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

        var listTopTap = mutableListOf<TopTapData>()
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))

        adapter2 = TopTapAdapter(requireContext(),R.layout.top_tap,listTopTap)
        listView = view.findViewById(R.id.lvRankingTap)

        listView.adapter = adapter2
    }
    private fun showDataXH(view: View) {
        val items = arrayListOf(
            Top_Item("Xa hoi", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("Xa hoi", "Subtitle 2", R.drawable.trikycamxuc,2.toString()),
            Top_Item("Xa hoi", "Subtitle 3",R.drawable.trikycamxuc,3.toString() ),
            Top_Item("Xa hoi", "Subtitle 4", R.drawable.trikycamxuc,4.toString()),
            Top_Item("Xa hoi", "Subtitle 5", R.drawable.trikycamxuc,5.toString()),
            Top_Item("Xa hoi", "Subtitle 6",R.drawable.trikycamxuc,6.toString() )
        )
        adapter = TopItemAdapter(items)
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

        var listTopTap = mutableListOf<TopTapData>()
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Xa hoi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Xa hoi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Xa hoi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))

        adapter2 = TopTapAdapter(requireContext(),R.layout.top_tap,listTopTap)
        listView = view.findViewById(R.id.lvRankingTap)

        listView.adapter = adapter2
    }
    private fun showDataNews(view: View) {
        val items = arrayListOf(
            Top_Item("Tin tuc 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("Tin tuc 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("Tin tuc 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("Tin tuc 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("Title 2", "Subtitle 2", R.drawable.trikycamxuc,2.toString()),
            Top_Item("Title 3", "Subtitle 3",R.drawable.trikycamxuc,3.toString() ),
            Top_Item("Title 4", "Subtitle 4", R.drawable.trikycamxuc,4.toString()),
            Top_Item("Title 5", "Subtitle 5", R.drawable.trikycamxuc,5.toString()),
            Top_Item("Title 6", "Subtitle 6",R.drawable.trikycamxuc,6.toString() )
        )
        adapter = TopItemAdapter(items)
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

        var listTopTap = mutableListOf<TopTapData>()
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Tin tuc ","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Tin tuc ","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Tin tuc ","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Tin tuc ","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Tin tuc ","Chủ nhật","10"))

        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))

        adapter2 = TopTapAdapter(requireContext(),R.layout.top_tap,listTopTap)
        listView = view.findViewById(R.id.lvRankingTap)

        listView.adapter = adapter2
    }
    private fun showDataComedy(view: View) {
        val items = arrayListOf(
            Top_Item("Comedy 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("TComedy 1", "Subtitle 2", R.drawable.trikycamxuc,2.toString()),
            Top_Item("Comedy 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("TComedy 1", "Subtitle 2", R.drawable.trikycamxuc,2.toString()),
            Top_Item("Comedy 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("TComedy 1", "Subtitle 2", R.drawable.trikycamxuc,2.toString()),
            Top_Item("Title 3", "Subtitle 3",R.drawable.trikycamxuc,3.toString() ),
            Top_Item("Title 4", "Subtitle 4", R.drawable.trikycamxuc,4.toString()),
            Top_Item("Title 5", "Subtitle 5", R.drawable.trikycamxuc,5.toString()),
            Top_Item("Title 6", "Subtitle 6",R.drawable.trikycamxuc,6.toString() )
        )
        adapter = TopItemAdapter(items)
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

        var listTopTap = mutableListOf<TopTapData>()
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Comdy","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Comdy","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Comdy","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Comdy","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))

        adapter2 = TopTapAdapter(requireContext(),R.layout.top_tap,listTopTap)
        listView = view.findViewById(R.id.lvRankingTap)

        listView.adapter = adapter2
    }
    private fun showDataBusiness(view: View) {
        val items = arrayListOf(
            Top_Item("Title 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("Title 2", "Subtitle 2", R.drawable.trikycamxuc,2.toString()),
            Top_Item("Title 3", "Subtitle 3",R.drawable.trikycamxuc,3.toString() ),
            Top_Item("Title 4", "Subtitle 4", R.drawable.trikycamxuc,4.toString()),
            Top_Item("Title 5", "Subtitle 5", R.drawable.trikycamxuc,5.toString()),
            Top_Item("Title 6", "Subtitle 6",R.drawable.trikycamxuc,6.toString() )
        )
        adapter = TopItemAdapter(items)
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

        var listTopTap = mutableListOf<TopTapData>()
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))

        adapter2 = TopTapAdapter(requireContext(),R.layout.top_tap,listTopTap)
        listView = view.findViewById(R.id.lvRankingTap)

        listView.adapter = adapter2
    }
    private fun showDataSport(view: View) {
        val items = arrayListOf(
            Top_Item("Title 1", "Subtitle 1", R.drawable.trikycamxuc, 1.toString()),
            Top_Item("Title 2", "Subtitle 2", R.drawable.trikycamxuc,2.toString()),
            Top_Item("Title 3", "Subtitle 3",R.drawable.trikycamxuc,3.toString() ),
            Top_Item("Title 4", "Subtitle 4", R.drawable.trikycamxuc,4.toString()),
            Top_Item("Title 5", "Subtitle 5", R.drawable.trikycamxuc,5.toString()),
            Top_Item("Title 6", "Subtitle 6",R.drawable.trikycamxuc,6.toString() )
        )
        adapter = TopItemAdapter(items)
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

        var listTopTap = mutableListOf<TopTapData>()
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))
        listTopTap.add(TopTapData(R.drawable.trikycamxuc,"Còn yêu được cứ yêu đi","Chủ nhật","10"))

        adapter2 = TopTapAdapter(requireContext(),R.layout.top_tap,listTopTap)
        listView = view.findViewById(R.id.lvRankingTap)

        listView.adapter = adapter2
    }


}




