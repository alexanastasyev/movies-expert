package com.example.mymovies.screens.movies.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.data.Movie
import com.example.mymovies.screens.ActivityUtils
import com.example.mymovies.screens.details.MovieDetailsActivity
import com.example.mymovies.screens.movies.OnMovieClickListener

class FavoriteMoviesFragment : Fragment(), FavoriteMoviesView {

    companion object {
        private const val COLUMNS_AMOUNT = 2
    }

    private lateinit var presenter: FavoriteMoviesPresenter
    private lateinit var recyclerView: RecyclerView
    private val adapter = MovieAdapterFavorite()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = FavoriteMoviesPresenter(this, this.requireContext())
        retainInstance = true
    }

    override fun showMovies(movies: List<Movie>) {
        adapter.movies.clear()
        adapter.addMovies(movies)
        adapter.notifyDataSetChanged()
    }

    override fun showError() {
        Toast.makeText(view?.context, getString(R.string.error_loading_movies), Toast.LENGTH_SHORT).show()
    }
}