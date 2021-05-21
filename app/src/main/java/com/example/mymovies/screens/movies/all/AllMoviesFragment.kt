package com.example.mymovies.screens.movies.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.mymovies.R
import com.example.mymovies.screens.movies.all.popular.PopularMoviesFragment
import com.example.mymovies.screens.movies.all.top.TopMoviesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AllMoviesFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)

        val tabs: List<String> = listOf(getString(R.string.movies_popular), getString(R.string.movies_best))
        val pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)

        viewPager.adapter = pagerAdapter
        pagerAdapter.update(listOf(PopularMoviesFragment(), TopMoviesFragment()))

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        super.onViewCreated(view, savedInstanceState)
    }
}