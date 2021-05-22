package com.alexanastasyev.mymovies.internet.responses

import com.google.gson.annotations.SerializedName

class GetMoviesResponse(
    @SerializedName("results")
    val movieModels: List<ServerMovieModel>
)