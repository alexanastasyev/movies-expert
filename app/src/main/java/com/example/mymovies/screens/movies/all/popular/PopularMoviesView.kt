package com.example.mymovies.screens.movies.all.popular

import com.example.mymovies.data.Movie

interface PopularMoviesView {
    fun showMovies(movies: List<Movie>)
    fun showError()
}