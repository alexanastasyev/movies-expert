package com.alexanastasyev.mymovies.screens.movies

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.internet.ads.AdsManager
import com.alexanastasyev.mymovies.screens.ActivityUtils
import com.alexanastasyev.mymovies.screens.movies.all.AllMoviesFragment
import com.alexanastasyev.mymovies.screens.movies.favorite.FavoriteMoviesFragment
import com.alexanastasyev.mymovies.screens.search.SearchMoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class StartActivity : AppCompatActivity() {

    companion object {
        private const val ALL_MOVIES_FRAGMENT_TAG = "ALL MOVIES"
        private const val FAVORITE_MOVIES_FRAGMENT_TAG = "FAVORITE MOVIES"
        private const val SEARCH_MOVIES_FRAGMENT_TAG = "SEARCH MOVIES"
    }

    private var allMoviesFragment = AllMoviesFragment()
    private val favoriteMoviesFragment = FavoriteMoviesFragment()
    private val searchMoviesFragment = SearchMoviesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        disableNightMode()

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_all -> {
                    showAllMoviesFragment()
                    true
                }
                R.id.menu_item_favorites -> {
                    showFavoriteMoviesFragment()
                    true
                }
                R.id.menu_item_search -> {
                    showSearchMoviesFragment()
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            showAllMoviesFragment()
        }

        AdsManager.loadBanner(findViewById(R.id.banner_ads), this)
    }

    override fun onResume() {
        ActivityUtils.closeAllActivityDetails = false
        super.onResume()
    }

    private fun disableNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun showAllMoviesFragment() {
        attachFragment(allMoviesFragment, ALL_MOVIES_FRAGMENT_TAG)
    }

    private fun showFavoriteMoviesFragment() {
        attachFragment(favoriteMoviesFragment, FAVORITE_MOVIES_FRAGMENT_TAG)
    }

    private fun showSearchMoviesFragment() {
        attachFragment(searchMoviesFragment, SEARCH_MOVIES_FRAGMENT_TAG)
    }

    override fun onBackPressed() {
        collapseApp()
    }

    private fun collapseApp() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

    private fun attachFragment(fragment: Fragment, tag: String) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            fragmentTransaction.add(R.id.fragment_container, fragment, tag)
            fragmentTransaction.addToBackStack(tag)
        } else {
            val fragmentToShow = supportFragmentManager.findFragmentByTag(tag)
            if (fragmentToShow != null) {
                fragmentTransaction.show(fragmentToShow)
            }
        }

        when (tag) {
            ALL_MOVIES_FRAGMENT_TAG -> {
                hideFragmentByTag(fragmentTransaction, FAVORITE_MOVIES_FRAGMENT_TAG)
                hideFragmentByTag(fragmentTransaction, SEARCH_MOVIES_FRAGMENT_TAG)
            }
            FAVORITE_MOVIES_FRAGMENT_TAG -> {
                hideFragmentByTag(fragmentTransaction, ALL_MOVIES_FRAGMENT_TAG)
                hideFragmentByTag(fragmentTransaction, SEARCH_MOVIES_FRAGMENT_TAG)
            }
            SEARCH_MOVIES_FRAGMENT_TAG -> {
                hideFragmentByTag(fragmentTransaction, ALL_MOVIES_FRAGMENT_TAG)
                hideFragmentByTag(fragmentTransaction, FAVORITE_MOVIES_FRAGMENT_TAG)
            }
        }
        fragmentTransaction.commit()
    }

    private fun hideFragmentByTag(fragmentTransaction: FragmentTransaction, tag: String) {
        val fragmentToHide = supportFragmentManager.findFragmentByTag(tag)
        if (fragmentToHide != null) {
            fragmentTransaction.hide(fragmentToHide)
        }
    }
}