package com.example.mymovies.internet

import com.example.mymovies.internet.responses.GetPopularMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServiceInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = NetworkUtils.DEFAULT_LANGUAGE,
        @Query("region") region: String = NetworkUtils.DEFAULT_REGION,
        @Query("page") page: Int
    ): Call<GetPopularMoviesResponse>

    @GET("movie/top_rated")
    fun getTopMovies(
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = NetworkUtils.DEFAULT_LANGUAGE
    ): Call<GetPopularMoviesResponse>
}