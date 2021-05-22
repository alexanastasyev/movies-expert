package com.alexanastasyev.mymovies.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class DatabaseMovieModel(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "rating")
    val rating: Int, // from 1 to 100

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "portraitPicturePath")
    var portraitPicturePath: String,

    @ColumnInfo(name = "landscapePicturePath")
    var landscapePicturePath: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)