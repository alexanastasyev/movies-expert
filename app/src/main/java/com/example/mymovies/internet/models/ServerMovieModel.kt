package com.example.mymovies.internet.models

import com.google.gson.annotations.SerializedName

class ServerMovieModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val description: String,

    @SerializedName("vote_average")
    val rating: Float,

    @SerializedName("vote_count")
    val votesAmount: Int,

    @SerializedName("release_date")
    val date: String,

    @SerializedName("poster_path")
    val smallPicturePath: String,

    @SerializedName("backdrop_path")
    val bigPicturePath: String
)