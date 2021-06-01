package com.alexanastasyev.mymovies.screens.movies.all

import com.alexanastasyev.mymovies.data.Movie

interface MoviesView {
    fun showMovies(movies: List<Movie>)
    fun showError()
}