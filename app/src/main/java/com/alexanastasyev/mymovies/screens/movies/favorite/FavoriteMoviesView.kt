package com.alexanastasyev.mymovies.screens.movies.favorite

import com.alexanastasyev.mymovies.data.Movie

interface FavoriteMoviesView {
    fun showMovies(movies: List<Movie>)
    fun showError()
}