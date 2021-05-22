package com.alexanastasyev.mymovies.internet

import com.alexanastasyev.mymovies.internet.responses.GetMoviesResponse
import com.alexanastasyev.mymovies.internet.responses.ServerMovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServerInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = NetworkUtils.DEFAULT_LANGUAGE,
        @Query("region") region: String = NetworkUtils.DEFAULT_REGION
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = NetworkUtils.DEFAULT_LANGUAGE,
        @Query("region") region: String = NetworkUtils.DEFAULT_REGION
    ): Call<GetMoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = NetworkUtils.DEFAULT_LANGUAGE
    ): Call<ServerMovieModel>
}