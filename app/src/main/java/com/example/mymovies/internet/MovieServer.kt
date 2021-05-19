package com.example.mymovies.internet

import com.example.mymovies.Movie
import com.example.mymovies.internet.converters.MovieConverter

object MovieServer {
    private val movieService = RetrofitMovieServer.getInstance()

    fun getPopularMovies(page: Int): List<Movie>? {
        val response = movieService.getPopularMovies(page = page).execute().body()
        return response?.movieModels?.let { MovieConverter.convert(it) }
    }

    fun getTopMovies(page: Int): List<Movie>? {
        val response = movieService.getTopMovies(page = page).execute().body()
        val movieModels =  response?.movieModels
        return if (movieModels != null) {
            MovieConverter.convert(movieModels.filter { it.votesAmount > NetworkUtils.MIN_VOTES })
        } else {
            null
        }
    }

    fun getMovieById(movieId: Int): Movie? {
        val movieModel = movieService.getMovieById(movieId = movieId).execute().body()
        return if (movieModel != null) {
            MovieConverter.convert(listOf(movieModel))[0]
        } else {
            null
        }
    }
}