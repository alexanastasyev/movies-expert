package com.example.mymovies

class Movie(
    val id: Int,
    val title: String,
    val date: String,
    val rating: Int, // from 1 to 100
    val description: String,
    var smallPicturePath: String,
    var bigPicturePath: String
) {
    override fun toString(): String {
        return "Movie(id=$id, title='$title', date='$date', rating=$rating, description='$description', smallPicturePath='$smallPicturePath', bigPicturePath='$bigPicturePath')"
    }
}