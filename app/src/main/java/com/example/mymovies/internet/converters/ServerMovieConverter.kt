package com.example.mymovies.internet.converters

import android.content.Context
import androidx.room.Room
import com.example.mymovies.data.Movie
import com.example.mymovies.database.AppDatabase
import com.example.mymovies.database.DatabaseUtils
import com.example.mymovies.internet.NetworkUtils
import com.example.mymovies.internet.responses.ServerMovieModel

object ServerMovieConverter {
    private const val RATING_FACTOR = 10
    private const val DATE_SEPARATOR = "-"
    private const val DATE_DAY_INDEX = 2
    private const val DATE_MONTH_INDEX = 1
    private const val DATE_YEAR_INDEX = 0

    fun convertModelsToMovies(serverMovieModels: List<ServerMovieModel>, context: Context): List<Movie> {
       return serverMovieModels.map { serverMovie ->
           val dates = serverMovie.date.split(DATE_SEPARATOR)
           Movie(
               id = serverMovie.id,
               title = serverMovie.title,
               date = "${dates[DATE_DAY_INDEX]}.${dates[DATE_MONTH_INDEX]}.${dates[DATE_YEAR_INDEX]}",
               rating = (serverMovie.rating * RATING_FACTOR).toInt(),
               description = serverMovie.description,
               portraitPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_SMALL_SIZE + serverMovie.smallPicturePath,
               landscapePicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_BIG_SIZE + serverMovie.bigPicturePath,
               isFavorite = isMovieFavorite(serverMovie.id, context)
           )
       }
    }

    private fun isMovieFavorite(id: Int, context: Context): Boolean {
        val database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DatabaseUtils.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
        return database.moviesDao().exist(id)
    }
}