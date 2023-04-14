package com.example.channel

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: HomeAdapter
    private lateinit var adapter2: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arrayListOf(
            Album("Title 1", "Subtitle 1", R.drawable.trikycamxuc),
            Album("Title 2", "Subtitle 2", R.drawable.trikycamxuc),
            Album("Title 3", "Subtitle 3",R.drawable.trikycamxuc ),
            Album("Title 4", "Subtitle 4", R.drawable.trikycamxuc),
            Album("Title 5", "Subtitle 5", R.drawable.trikycamxuc),
            Album("Title 6", "Subtitle 6",R.drawable.trikycamxuc ),
            Album("Title 3", "Subtitle 3",R.drawable.trikycamxuc ),
            Album("Title 4", "Subtitle 4", R.drawable.trikycamxuc),
            Album("Title 5", "Subtitle 5", R.drawable.trikycamxuc),
            Album("Title 6", "Subtitle 6",R.drawable.trikycamxuc )
        )

        val items2 = arrayListOf(
            Album("Title 1", "Subtitle 1", R.drawable.trikycamxuc),
            Album("Title 2", "Subtitle 2", R.drawable.trikycamxuc),
            Album("Title 3", "Subtitle 3",R.drawable.trikycamxuc ),
            Album("Title 4", "Subtitle 4", R.drawable.trikycamxuc),
            Album("Title 5", "Subtitle 5", R.drawable.trikycamxuc),
            Album("Title 6", "Subtitle 6",R.drawable.trikycamxuc )
        )
        adapter = HomeAdapter(items)
        adapter2 = HomeAdapter(items2)
        adapter.onItemClick = { album ->
            // Handle click events on album items here
        }
        viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager2)
        viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }
        viewPager2.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }
        viewPager.adapter = adapter
        viewPager2.adapter = adapter2
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((5 * Resources.getSystem().displayMetrics.density).toInt()))
        viewPager.setPageTransformer(compositePageTransformer)
        viewPager2.setPageTransformer(compositePageTransformer)
    }
}