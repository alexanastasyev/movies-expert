package com.alexanastasyev.mymovies.screens.movies.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.screens.ActivityUtils
import com.alexanastasyev.mymovies.screens.details.MovieDetailsActivity
import com.alexanastasyev.mymovies.screens.movies.OnMovieClickListener
import com.google.android.material.snackbar.Snackbar

class FavoriteMoviesFragment : Fragment(), FavoriteMoviesView {

    companion object {
        private const val COLUMNS_AMOUNT = 2
    }

    private lateinit var presenter: FavoriteMoviesPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewNoFavorites: TextView
    private val adapter = MovieAdapterFavorite()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textViewNoFavorites = view.findViewById(R.id.text_view_no_favorites)
        recyclerView = view.findViewById(R.id.fragment_recycler_movies_favorite)
        recyclerView.layoutManager = GridLayoutManager(this.requireContext(), COLUMNS_AMOUNT)
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
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadMoviesFromDatabase()
    }

    override fun onDestroyView() {
        presenter.disposeDisposable()
        presenter.closeDatabase()
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = FavoriteMoviesPresenter(this, this.requireContext())
        retainInstance = true
    }

    override fun showMovies(movies: List<Movie>) {
        if (movies.isEmpty()) {
            textViewNoFavorites.visibility = View.VISIBLE
        } else {
            textViewNoFavorites.visibility = View.INVISIBLE
        }
        adapter.movies.clear()
        adapter.addMovies(movies)
        adapter.notifyDataSetChanged()
    }

    override fun showError() {
        Snackbar.make(recyclerView, getString(R.string.error_loading_movies), Snackbar.LENGTH_SHORT).show()
    }
}