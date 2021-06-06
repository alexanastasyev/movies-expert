package com.alexanastasyev.mymovies.screens.search

import com.alexanastasyev.mymovies.data.Movie

interface SearchMoviesView {
    fun showNewMovies(movies: List<Movie>)
    fun addMovies(movies: List<Movie>)
    fun stopPagination()
    fun resumePagination()
    fun showError()
}