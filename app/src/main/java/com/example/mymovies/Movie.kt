package com.example.mymovies

import android.graphics.Bitmap

class Movie(
    private val id: Int,
    private val name: String,
    private val author: String,
    private val year: Int,
    private val rating: Int, // from 1 to 100
    private val description: String,
    private var smallPicture: Bitmap,
    private var bigPicture: Bitmap
)