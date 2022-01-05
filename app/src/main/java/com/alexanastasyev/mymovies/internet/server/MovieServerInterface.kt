package com.alexanastasyev.mymovies.internet.server

import com.alexanastasyev.mymovies.internet.server.responses.GetMovieDetailsResponse
import com.alexanastasyev.mymovies.internet.server.responses.GetMoviesResponse
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

    @GET("movie/now_playing")
    fun getNewMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = NetworkUtils.DEFAULT_LANGUAGE
    ): Call<GetMoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = NetworkUtils.DEFAULT_LANGUAGE
    ): Call<GetMovieDetailsResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = NetworkUtils.DEFAULT_LANGUAGE
    ): Call<GetMoviesResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = NetworkUtils.DEFAULT_LANGUAGE,
        @Query("region") region: String = NetworkUtils.DEFAULT_REGION
    ): Call<GetMoviesResponse>
}