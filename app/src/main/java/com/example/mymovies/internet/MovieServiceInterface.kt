package com.example.mymovies.internet

import com.example.mymovies.internet.responses.GetPopularMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServiceInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "d0c314843449830d4310a88c038551d5",
        @Query("language") language: String = "ru-RU"
    ): Call<GetPopularMoviesResponse>
}