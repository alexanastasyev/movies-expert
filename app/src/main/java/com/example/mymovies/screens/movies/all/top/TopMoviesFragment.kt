package com.example.mymovies.screens.movies.all.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.Movie
import com.example.mymovies.R
import com.example.mymovies.screens.movies.all.MovieAdapter

class TopMoviesFragment : Fragment(), TopMoviesView {

    companion object {
        private const val PAGINATION_NUMBER = 5
    }

    private val presenter = TopMoviesPresenter(this)

    private lateinit var startingProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val adapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startingProgressBar = view.findViewById(R.id.starting_progress_bar)
        recyclerView = view.findViewById(R.id.fragment_recycler_movies_new)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter

        presenter.loadNextPage()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = recyclerView.layoutManager?.itemCount
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (totalItemCount != null) {
                    if (totalItemCount - lastVisibleItemPosition <= PAGINATION_NUMBER) {
                        presenter.loadNextPage()
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
        Toast.makeText(view?.context, getString(R.string.error_loading_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        startingProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        startingProgressBar.visibility = View.GONE
    }
}