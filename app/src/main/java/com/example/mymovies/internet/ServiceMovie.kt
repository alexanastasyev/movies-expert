package com.example.mymovies.internet

import com.google.gson.annotations.SerializedName

class ServiceMovie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val description: String,

    @SerializedName("vote_average")
    val rating: Float
)