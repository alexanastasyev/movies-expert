package com.example.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)

        val tabs: List<String> = listOf(getString(R.string.movies_new), getString(R.string.movies_best))
        val pagerAdapter = PagerAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = pagerAdapter
        pagerAdapter.update(listOf(NewMoviesFragment(), TopMoviesFragment()))

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}