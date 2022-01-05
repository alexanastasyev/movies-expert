package com.alexanastasyev.mymovies.internet.server

import android.content.Context
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.internet.server.converters.ServerMovieConverter
import java.util.*

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

    fun getNewMovies(page: Int, context: Context): List<Movie>? {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        val response = movieService.getNewMovies(page = page).execute().body()
        val movieModels =  response?.movieModels
        return if (movieModels != null) {
            ServerMovieConverter.convertModelsToMovies(movieModels.filter {
                val movieYear = it.date.split(NetworkUtils.DATE_DELIMITER_SERVER)[NetworkUtils.DATE_YEAR_INDEX_SERVER].toInt()
                movieYear == currentYear || movieYear == currentYear - 1
            }, context)
        } else {
            null
        }
    }

    fun getMovieById(movieId: Int, context: Context): Movie? {
        val movieModel = movieService.getMovieById(movieId = movieId).execute().body()
        return if (movieModel != null) {
            ServerMovieConverter.convertModelToMovie(movieModel, context)
        } else {
            null
        }
    }

    fun getSimilarMovies(movieId: Int, context: Context): List<Movie>? {
        val response = movieService.getSimilarMovies(movieId = movieId).execute().body()
        return response?.movieModels?.let { ServerMovieConverter.convertModelsToMovies(it, context) }
    }

    fun searchMovie(query: String, page: Int, context: Context): List<Movie>? {
        val response = movieService.searchMovies(query = query, page = page).execute().body()
        return response?.movieModels?.let { ServerMovieConverter.convertModelsToMovies(it, context) }
    }
}