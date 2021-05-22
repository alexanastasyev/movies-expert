package com.alexanastasyev.mymovies.screens.movies.all.top

import com.alexanastasyev.mymovies.data.Movie

interface TopMoviesView {
    fun showMovies(movies: List<Movie>)
    fun showError()
}