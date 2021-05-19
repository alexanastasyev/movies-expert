package com.example.mymovies.internet.converters

import com.example.mymovies.Movie
import com.example.mymovies.internet.NetworkUtils
import com.example.mymovies.internet.responses.ServerMovieModel

object MovieConverter {
    private const val RATING_FACTOR = 10
    private const val DATE_SEPARATOR = "-"
    private const val DATE_DAY_INDEX = 2
    private const val DATE_MONTH_INDEX = 1
    private const val DATE_YEAR_INDEX = 0

    fun convert(serverMovieModels: List<ServerMovieModel>): List<Movie> {
       return serverMovieModels.map { serverMovie ->
           val dates = serverMovie.date.split(DATE_SEPARATOR)
           Movie(
               id = serverMovie.id,
               title = serverMovie.title,
               date = "${dates[DATE_DAY_INDEX]}.${dates[DATE_MONTH_INDEX]}.${dates[DATE_YEAR_INDEX]}",
               rating = (serverMovie.rating * RATING_FACTOR).toInt(),
               description = serverMovie.description,
               smallPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_SMALL_SIZE + serverMovie.smallPicturePath,
               bigPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_BIG_SIZE + serverMovie.bigPicturePath
           )
       }
    }
}