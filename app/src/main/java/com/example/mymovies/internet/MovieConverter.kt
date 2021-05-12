package com.example.mymovies.internet

import com.example.mymovies.Movie

object MovieConverter {
    fun convert(serviceMovieModels: List<ServiceMovieModel>): List<Movie> {
       return serviceMovieModels.map { serviceMovie ->
           Movie(
                   id = serviceMovie.id,
                   title = serviceMovie.title,
                   date = serviceMovie.date,
                   rating = (serviceMovie.rating * 10).toInt(),
                   description = serviceMovie.description,
                   smallPicturePath = serviceMovie.smallPicturePath,
                   bigPicturePath = serviceMovie.bigPicturePath
           )
       }
    }
}