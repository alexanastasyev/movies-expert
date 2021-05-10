package com.example.mymovies.internet

object MovieService {
    fun getPopularMovies(): List<ServiceMovie>? {
        val movieService = RetrofitMovieService.getInstance()
        val response = movieService.getPopularMovies().execute().body()
        return response?.movies
    }
}