package com.example.mymovies.internet.models

import com.example.mymovies.Movie
import com.example.mymovies.internet.NetworkUtils

object MovieConverter {
    private const val RATING_FACTOR = 10

    fun convert(serverMovieModels: List<ServerMovieModel>): List<Movie> {
       return serverMovieModels.map { serverMovie ->
           Movie(
               id = serverMovie.id,
               title = serverMovie.title,
               date = serverMovie.date,
               rating = (serverMovie.rating * RATING_FACTOR).toInt(),
               description = serverMovie.description,
               smallPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_SMALL_SIZE + serverMovie.smallPicturePath,
               bigPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_BIG_SIZE + serverMovie.bigPicturePath
           )
       }
    }
}