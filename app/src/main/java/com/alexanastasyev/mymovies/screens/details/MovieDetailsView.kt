package com.alexanastasyev.mymovies.screens.details

import com.alexanastasyev.mymovies.data.Movie

interface MovieDetailsView {
    fun showMovieInfo(movie: Movie)
    fun showError()
}