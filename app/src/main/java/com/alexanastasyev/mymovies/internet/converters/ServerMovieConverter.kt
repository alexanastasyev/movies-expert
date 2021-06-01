package com.alexanastasyev.mymovies.internet.converters

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.database.AppDatabase
import com.alexanastasyev.mymovies.database.DatabaseUtils
import com.alexanastasyev.mymovies.internet.NetworkUtils
import com.alexanastasyev.mymovies.internet.responses.ServerMovieModel

object ServerMovieConverter {
    private const val RATING_FACTOR = 10

    fun convertModelsToMovies(serverMovieModels: List<ServerMovieModel>, context: Context): List<Movie> {
       return serverMovieModels.map { serverMovie ->
           @Suppress("UselessCallOnNotNull")
           val date = if (serverMovie.date.isNullOrEmpty()) {
               ""
           } else {
               val dates = serverMovie.date.split(NetworkUtils.DATE_DELIMITER)
               "${dates[NetworkUtils.DATE_DAY_INDEX]}.${dates[NetworkUtils.DATE_MONTH_INDEX]}.${dates[NetworkUtils.DATE_YEAR_INDEX]}"
           }
           Movie(
               id = serverMovie.id,
               title = serverMovie.title,
               date = date,
               rating = (serverMovie.rating * RATING_FACTOR).toInt(),
               description = serverMovie.description,
               portraitPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_PORTRAIT_SIZE + serverMovie.smallPicturePath,
               landscapePicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_LANDSCAPE_SIZE + serverMovie.bigPicturePath,
               isFavorite = isMovieFavorite(serverMovie.id, context)
           )
       }
    }

    private fun isMovieFavorite(id: Int, context: Context): Boolean {
        val database = databaseBuilder(
            context,
            AppDatabase::class.java,
            DatabaseUtils.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
        val result = database.moviesDao().exist(id)
        database.close()
        return result
    }
}