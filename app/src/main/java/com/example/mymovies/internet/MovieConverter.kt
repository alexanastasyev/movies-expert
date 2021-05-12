package com.example.mymovies.internet

import com.example.mymovies.Movie

object MovieConverter {
    fun convert(serverMovieModels: List<ServerMovieModel>): List<Movie> {
       return serverMovieModels.map { serverMovie ->
           Movie(
                   id = serverMovie.id,
                   title = serverMovie.title,
                   date = serverMovie.date,
                   rating = (serverMovie.rating * 10).toInt(),
                   description = serverMovie.description,
                   smallPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_SMALL_SIZE + serverMovie.smallPicturePath,
                   bigPicturePath = NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_BIG_SIZE + serverMovie.bigPicturePath
           )
       }
    }
}