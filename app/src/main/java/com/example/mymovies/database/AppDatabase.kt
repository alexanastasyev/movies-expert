package com.example.mymovies.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DatabaseMovieModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDAO
}