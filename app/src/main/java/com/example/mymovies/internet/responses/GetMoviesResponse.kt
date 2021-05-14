package com.example.mymovies.internet.responses

import com.example.mymovies.internet.models.ServerMovieModel
import com.google.gson.annotations.SerializedName

class GetMoviesResponse(
    @SerializedName("results")
    val movieModels: List<ServerMovieModel>
)