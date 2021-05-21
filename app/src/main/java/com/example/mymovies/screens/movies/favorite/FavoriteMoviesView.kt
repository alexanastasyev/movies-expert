package com.example.mymovies.screens.movies.favorite

import com.example.mymovies.data.Movie

interface FavoriteMoviesView {
    fun showMovies(movies: List<Movie>)
    fun showError()
}