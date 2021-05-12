package com.example.mymovies.internet

import com.google.gson.annotations.SerializedName

class ServiceMovieModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val description: String,

    @SerializedName("vote_average")
    val rating: Float,

    @SerializedName("release_date")
    val date: String,

    @SerializedName("poster_path")
    val smallPicturePath: String,

    @SerializedName("backdrop_path")
    val bigPicturePath: String
)