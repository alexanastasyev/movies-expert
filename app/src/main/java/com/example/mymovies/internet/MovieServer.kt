package com.example.mymovies.internet

import android.util.Log
import com.example.mymovies.Movie
import io.reactivex.Single

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