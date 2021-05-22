package com.alexanastasyev.mymovies.screens.movies.all.popular

import com.alexanastasyev.mymovies.data.Movie

interface PopularMoviesView {
    fun showMovies(movies: List<Movie>)
    fun showError()
}