package com.example.mymovies.screens.movies.all.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymovies.R
import com.example.mymovies.internet.ServiceMovie

class PopularMoviesFragment : Fragment(), PopularMoviesView {

    private val presenter = PopularMoviesPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.loadPopularMovies()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        presenter.disposeDisposable()
        super.onDestroyView()
    }

    override fun showMovies(movies: List<ServiceMovie>) {
        (view as TextView).text = movies.map { it.title }.toString()
    }

    override fun showError() {
        Toast.makeText(view?.context, "Error while loading movies", Toast.LENGTH_SHORT).show()
    }
}