package com.example.mymovies.screens.details

import com.example.mymovies.Movie

interface MovieDetailsView {
    fun showMovieInfo(movie: Movie)
    fun showError()
}