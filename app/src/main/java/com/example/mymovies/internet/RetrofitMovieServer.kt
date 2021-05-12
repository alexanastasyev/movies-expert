package com.example.mymovies.internet

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitMovieServer {

    private val retrofit = Retrofit.Builder()
        .baseUrl(NetworkUtils.SERVICE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieServiceInterface::class.java)

    fun getInstance() : MovieServiceInterface {
        return movieService
    }
}