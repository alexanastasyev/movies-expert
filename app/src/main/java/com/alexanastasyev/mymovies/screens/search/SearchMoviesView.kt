package com.alexanastasyev.mymovies.screens.search

import com.alexanastasyev.mymovies.data.Movie

interface SearchMoviesView {
    fun showMovies(movies: List<Movie>)
    fun showError()
}