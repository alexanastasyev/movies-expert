package com.alexanastasyev.mymovies.internet.server.converters

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.alexanastasyev.mymovies.data.Genre
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.database.AppDatabase
import com.alexanastasyev.mymovies.database.DatabaseUtils
import com.alexanastasyev.mymovies.internet.server.NetworkUtils
import com.alexanastasyev.mymovies.internet.server.responses.GetMovieDetailsResponse
import com.alexanastasyev.mymovies.internet.server.responses.ServerMovieModel

object ServerMovieConverter {
    private const val RATING_FACTOR = 10

    fun convertModelsToMovies(serverMovieModels: List<ServerMovieModel>, context: Context): List<Movie> {
       return serverMovieModels.map { serverMovie ->
           @Suppress("UselessCallOnNotNull")
           val date = if (serverMovie.date.isNullOrEmpty()) {
               ""
           } else {
               val dates = serverMovie.date.split(NetworkUtils.DATE_DELIMITER_SERVER)
               "${dates[NetworkUtils.DATE_DAY_INDEX_SERVER]}.${dates[NetworkUtils.DATE_MONTH_INDEX_SERVER]}.${dates[NetworkUtils.DATE_YEAR_INDEX_SERVER]}"
           }
           val genres = if(!serverMovie.genreIds.isNullOrEmpty()) {
               getGenresAsString(serverMovie.genreIds)
           } else {
               ""
           }
           Movie(
               id = serverMovie.id,
               title = serverMovie.title,
               date = date,
               rating = (serverMovie.rating * RATING_FACTOR).toInt(),
               description = serverMovie.description,
               portraitPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_PORTRAIT_SIZE + serverMovie.smallPicturePath,
               landscapePicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_LANDSCAPE_SIZE + serverMovie.bigPicturePath,
               isFavorite = isMovieFavorite(serverMovie.id, context),
               genres = genres
           )
       }
    }

    fun convertModelToMovie(serverMovie: GetMovieDetailsResponse, context: Context): Movie {
        @Suppress("UselessCallOnNotNull")
        val date = if (serverMovie.date.isNullOrEmpty()) {
            ""
        } else {
            val dates = serverMovie.date.split(NetworkUtils.DATE_DELIMITER_SERVER)
            "${dates[NetworkUtils.DATE_DAY_INDEX_SERVER]}.${dates[NetworkUtils.DATE_MONTH_INDEX_SERVER]}.${dates[NetworkUtils.DATE_YEAR_INDEX_SERVER]}"
        }
        val genres = if(!serverMovie.genres.isNullOrEmpty()) {
            getGenresAsString(serverMovie.genres.map { it.id })
        } else {
            ""
        }
        return Movie(
            id = serverMovie.id,
            title = serverMovie.title,
            date = date,
            rating = (serverMovie.rating * RATING_FACTOR).toInt(),
            description = serverMovie.description,
            portraitPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_PORTRAIT_SIZE + serverMovie.smallPicturePath,
            landscapePicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_LANDSCAPE_SIZE + serverMovie.bigPicturePath,
            isFavorite = isMovieFavorite(serverMovie.id, context),
            genres = genres
        )
    }

    private fun getGenresAsString(genreIds: List<Int>): String {
        val builder = StringBuilder()
        builder.append(Genre.getById(genreIds[0]).title)
        for (i in 1 until genreIds.size) {
            builder.append(", ").append(Genre.getById(genreIds[i]).title)
        }
        return builder.toString()
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