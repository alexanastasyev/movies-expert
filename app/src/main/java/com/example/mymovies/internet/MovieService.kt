package com.example.mymovies.internet

import com.example.mymovies.Movie

object MovieService {
    fun getPopularMovies(): List<Movie>? {
        val movieService = RetrofitMovieService.getInstance()
        val response = movieService.getPopularMovies().execute().body()
        return response?.movies?.let { MovieConverter.convert(it) }
    }
}