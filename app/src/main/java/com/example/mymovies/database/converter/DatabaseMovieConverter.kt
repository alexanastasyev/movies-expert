package com.example.mymovies.database.converter

import com.example.mymovies.data.Movie
import com.example.mymovies.database.models.DatabaseMovieModel

object DatabaseMovieConverter {

    fun convertModelsToMovies(models: List<DatabaseMovieModel>): List<Movie> {
        val result = arrayListOf<Movie>()
        for (model in models) {
            result.add(Movie(
                id = model.id,
                title = model.title,
                date = model.date,
                rating = model.rating,
                description = model.description,
                portraitPicturePath = model.portraitPicturePath,
                landscapePicturePath = model.landscapePicturePath,
                isFavorite = model.isFavorite
            ))
        }
        return result
    }

    fun convertMoviesToModels(movies: List<Movie>): List<DatabaseMovieModel> {
        val result = arrayListOf<DatabaseMovieModel>()
        for (movie in movies) {
            result.add(DatabaseMovieModel(
                id = movie.id,
                title = movie.title,
                date = movie.date,
                rating = movie.rating,
                description = movie.description,
                portraitPicturePath = movie.portraitPicturePath,
                landscapePicturePath = movie.landscapePicturePath,
                isFavorite = movie.isFavorite
            ))
        }
        return result
    }
}