package com.example.mymovies.screens.details

import com.example.mymovies.data.Movie

interface MovieDetailsView {
    fun showMovieInfo(movie: Movie)
    fun showError()
}