package com.example.mymovies.database

import android.content.Context
import androidx.room.Room

object Database {

    private const val DATABASE_NAME = "database.db"
    private lateinit var instance: AppDatabase

    fun get(): AppDatabase {
        synchronized(this) {
                return instance
        }
    }

    fun initDatabase(context: Context) {
        instance = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

}