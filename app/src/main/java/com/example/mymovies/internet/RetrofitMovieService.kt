package com.example.mymovies.internet

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitMovieService {
    private const val baseUrl = "https://api.themoviedb.org/3/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieServiceInterface::class.java)

    fun getInstance() : MovieServiceInterface {
        return movieService
    }
}