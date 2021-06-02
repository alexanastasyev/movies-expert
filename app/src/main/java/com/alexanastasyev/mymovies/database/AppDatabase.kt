package com.alexanastasyev.mymovies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexanastasyev.mymovies.database.daos.MoviesDAO
import com.alexanastasyev.mymovies.database.models.DatabaseMovieModel

@Database(
    entities = [DatabaseMovieModel::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDAO
}