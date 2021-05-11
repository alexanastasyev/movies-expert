package com.example.mymovies.internet

import com.example.mymovies.internet.responses.GetPopularMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServiceInterface {

    companion object {
        private const val BASE_LANGUAGE = "ru-RU"
        private const val API_KEY = "d0c314843449830d4310a88c038551d5"
    }

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = BASE_LANGUAGE
    ): Call<GetPopularMoviesResponse>

    @GET("movie/top_rated")
    fun getTopMovies(
            @Query("api_key") apiKey: String = API_KEY,
            @Query("language") language: String = BASE_LANGUAGE
    ): Call<GetPopularMoviesResponse>
}