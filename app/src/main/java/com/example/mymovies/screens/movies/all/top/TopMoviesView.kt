package com.example.mymovies.screens.movies.all.top

import com.example.mymovies.Movie

interface TopMoviesView {
    fun showMovies(movies: List<Movie>)
    fun showError()
}