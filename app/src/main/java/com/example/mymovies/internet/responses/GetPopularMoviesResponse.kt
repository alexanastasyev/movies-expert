package com.example.mymovies.internet.responses

import com.example.mymovies.internet.ServiceMovie
import com.google.gson.annotations.SerializedName

class GetPopularMoviesResponse(
    @SerializedName("results")
    val movies: List<ServiceMovie>
)