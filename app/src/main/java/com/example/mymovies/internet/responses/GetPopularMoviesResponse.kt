package com.example.mymovies.internet.responses

import com.example.mymovies.internet.ServiceMovieModel
import com.google.gson.annotations.SerializedName

class GetPopularMoviesResponse(
    @SerializedName("results")
    val movieModels: List<ServiceMovieModel>
)