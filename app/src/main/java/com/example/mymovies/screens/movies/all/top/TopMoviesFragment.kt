package com.example.mymovies.screens.movies.all.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymovies.Movie
import com.example.mymovies.R

class TopMoviesFragment : Fragment(), TopMoviesView {

    private val presenter = TopMoviesPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.loadTopMovies()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        presenter.disposeDisposable()
        super.onDestroyView()
    }

    override fun showMovies(movies: List<Movie>) {
        //(view as TextView).text = movies.toString()
    }

    override fun showError() {
        Toast.makeText(view?.context, getString(R.string.error_loading_movies), Toast.LENGTH_SHORT).show()
    }
}