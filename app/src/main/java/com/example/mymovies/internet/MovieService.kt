package com.example.mymovies.internet

import com.example.mymovies.Movie

object MovieService {
    private val movieService = RetrofitMovieService.getInstance()

    fun getPopularMovies(): List<Movie>? {
        val response = movieService.getPopularMovies().execute().body()
        return response?.movies?.let { MovieConverter.convert(it) }
    }

    fun getTopMovies(): List<Movie>? {
        val response = movieService.getTopMovies().execute().body()
        return response?.movies?.let { MovieConverter.convert(it) }
    }
}