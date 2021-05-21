package com.example.mymovies.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDAO {
    @Query("SELECT * FROM movies")
    fun getAll(): List<DatabaseMovieModel>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Int): DatabaseMovieModel

    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE id = :id)")
    fun exist(id: Int): Boolean

    @Insert
    fun insert(movies: DatabaseMovieModel)

    @Delete
    fun delete(movie: DatabaseMovieModel)
}