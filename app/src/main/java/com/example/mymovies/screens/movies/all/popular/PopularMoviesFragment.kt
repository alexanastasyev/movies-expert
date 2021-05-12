package com.example.mymovies.screens.movies.all.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.Movie
import com.example.mymovies.screens.movies.all.MovieAdapter
import com.example.mymovies.R

class PopularMoviesFragment : Fragment(), PopularMoviesView {

    private val presenter = PopularMoviesPresenter(this)
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.fragment_recycler_movies_new)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        presenter.loadPopularMovies()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        presenter.disposeDisposable()
        super.onDestroyView()
    }

    override fun showMovies(movies: List<Movie>) {
        recyclerView.adapter = MovieAdapter(movies)
    }

    override fun showError() {
        Toast.makeText(view?.context, getString(R.string.error_loading_movies), Toast.LENGTH_SHORT).show()
    }
}