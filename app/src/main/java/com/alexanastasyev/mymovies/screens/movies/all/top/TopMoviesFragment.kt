package com.alexanastasyev.mymovies.screens.movies.all.top

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.screens.ActivityUtils
import com.alexanastasyev.mymovies.screens.details.MovieDetailsActivity
import com.alexanastasyev.mymovies.screens.movies.OnMovieClickListener
import com.alexanastasyev.mymovies.screens.movies.all.MovieAdapterAll
import com.alexanastasyev.mymovies.screens.movies.all.MoviesView
import com.google.android.material.snackbar.Snackbar

class TopMoviesFragment : Fragment(), MoviesView {

    companion object {
        private const val PAGINATION_NUMBER = 5
    }

    private val presenter = TopMoviesPresenter(this)

    private lateinit var startingProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val adapter = MovieAdapterAll()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startingProgressBar = view.findViewById(R.id.starting_progress_bar)
        recyclerView = view.findViewById(R.id.fragment_recycler_movies_all)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        adapter.onMovieClickListener = object : OnMovieClickListener {
            override fun onMovieClick(position: Int) {
                val movieId = adapter.movies[position].id
                val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                    putExtra(ActivityUtils.MOVIE_ID_KEY, movieId)
                }
                startActivity(intent)
            }
        }
        recyclerView.adapter = adapter

        presenter.loadNextPage(requireContext())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = recyclerView.layoutManager?.itemCount
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (totalItemCount != null) {
                    if (totalItemCount - lastVisibleItemPosition <= PAGINATION_NUMBER) {
                        presenter.loadNextPage(requireContext())
                    }

                    if (lastVisibleItemPosition == totalItemCount - 1) {
                        showLoading()
                    }
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onDestroy() {
        presenter.disposeDisposable()
        super.onDestroy()
    }

    override fun showMovies(movies: List<Movie>) {
        hideLoading()
        adapter.addMovies(movies)
        adapter.notifyDataSetChanged()
    }

    override fun showError() {
        Snackbar.make(recyclerView, getString(R.string.error_loading_movies), Snackbar.LENGTH_SHORT).show()
        hideLoading()
    }

    private fun showLoading() {
        startingProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        startingProgressBar.visibility = View.GONE
    }
}