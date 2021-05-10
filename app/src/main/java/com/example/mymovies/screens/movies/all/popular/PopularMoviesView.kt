package com.example.mymovies.screens.movies.all.popular

import com.example.mymovies.Movie
import com.example.mymovies.internet.ServiceMovie

interface PopularMoviesView {
    fun showMovies(movies: List<ServiceMovie>)
    fun showError()
}