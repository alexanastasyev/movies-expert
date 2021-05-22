package com.alexanastasyev.mymovies.database.daos

import androidx.room.*
import com.alexanastasyev.mymovies.database.models.DatabaseMovieModel

@Dao
interface MoviesDAO {
    @Query("SELECT * FROM movies ORDER BY title")
    fun getAll(): List<DatabaseMovieModel>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Int): DatabaseMovieModel

    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE id = :id)")
    fun exist(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: DatabaseMovieModel)

    @Delete
    fun delete(movie: DatabaseMovieModel)
}