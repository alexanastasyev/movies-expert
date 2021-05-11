package com.example.mymovies.internet

import com.example.mymovies.Movie

object MovieConverter {
    fun convert(serviceMovies: List<ServiceMovie>): List<Movie> {
       return serviceMovies.map { serviceMovie ->
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