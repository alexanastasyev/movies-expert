package com.example.mymovies.internet

import android.content.Context
import com.example.mymovies.data.Movie
import com.example.mymovies.internet.converters.ServerMovieConverter

object MovieServer {
    private val movieService = RetrofitMovieServer.getInstance()

    fun getPopularMovies(page: Int, context: Context): List<Movie>? {
        val response = movieService.getPopularMovies(page = page).execute().body()
        return response?.movieModels?.let { ServerMovieConverter.convertModelsToMovies(it, context) }
    }

    fun getTopMovies(page: Int, context: Context): List<Movie>? {
        val response = movieService.getTopMovies(page = page).execute().body()
        val movieModels =  response?.movieModels
        return if (movieModels != null) {
            ServerMovieConverter.convertModelsToMovies(movieModels.filter { it.votesAmount > NetworkUtils.MIN_VOTES }, context)
        } else {
            null
        }
    }

    fun getMovieById(movieId: Int, context: Context): Movie? {
        val movieModel = movieService.getMovieById(movieId = movieId).execute().body()
        return if (movieModel != null) {
            ServerMovieConverter.convertModelsToMovies(listOf(movieModel), context)[0]
        } else {
            null
        }
    }
}