package com.example.mymovies.screens.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.mymovies.R
import com.example.mymovies.screens.movies.all.AllMoviesFragment
import com.example.mymovies.screens.movies.favorite.FavoriteMoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class StartActivity : AppCompatActivity() {

    companion object {
        private const val ALL_MOVIES_FRAGMENT_TAG = "ALL MOVIES"
        private const val FAVORITE_MOVIES_FRAGMENT_TAG = "FAVORITE MOVIES"
    }

    private val allMoviesFragment = AllMoviesFragment()
    private val favoriteMoviesFragment = FavoriteMoviesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

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
                else -> false
            }
        }
        showAllMoviesFragment()
    }

    private fun showAllMoviesFragment() {
        attachFragment(allMoviesFragment, ALL_MOVIES_FRAGMENT_TAG)
    }

    private fun showFavoriteMoviesFragment() {
        attachFragment(favoriteMoviesFragment, FAVORITE_MOVIES_FRAGMENT_TAG)
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
            }
            FAVORITE_MOVIES_FRAGMENT_TAG -> {
                hideFragmentByTag(fragmentTransaction, ALL_MOVIES_FRAGMENT_TAG)
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