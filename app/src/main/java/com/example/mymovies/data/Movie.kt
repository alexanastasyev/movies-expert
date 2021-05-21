package com.example.mymovies.data

class Movie(
    val id: Int,
    val title: String,
    val date: String,
    val rating: Int, // from 1 to 100
    val description: String,
    var portraitPicturePath: String,
    var landscapePicturePath: String,
    var isFavorite: Boolean
)