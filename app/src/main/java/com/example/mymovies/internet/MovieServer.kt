package com.example.mymovies.internet

import com.example.mymovies.Movie

object MovieServer {
    private val movieService = RetrofitMovieServer.getInstance()

    fun getPopularMovies(page: Int): List<Movie>? {
        val response = movieService.getPopularMovies(page = page).execute().body()
        return response?.movieModels?.let { MovieConverter.convert(it) }
    }

    fun getTopMovies(): List<Movie>? {
        val response = movieService.getTopMovies().execute().body()
        return response?.movieModels?.let { MovieConverter.convert(it) }
    }
}