package com.alexanastasyev.mymovies.screens.movies.favorite

import android.content.Context
import androidx.room.Room
import com.alexanastasyev.mymovies.database.AppDatabase
import com.alexanastasyev.mymovies.database.DatabaseUtils
import com.alexanastasyev.mymovies.database.converter.DatabaseMovieConverter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoriteMoviesPresenter(
    private val view: FavoriteMoviesView,
    context: Context
) {
    private val compositeDisposable = CompositeDisposable()
    private val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DatabaseUtils.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    fun loadMoviesFromDatabase() {
        val disposable = Single.fromCallable { database.moviesDao().getAll() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ models ->
                if (models != null) {
                    view.showMovies(DatabaseMovieConverter.convertModelsToMovies(models))
                } else {
                    view.showError()
                }
            }, {
                view.showError()
            })
        compositeDisposable.add(disposable)
    }

    fun disposeDisposable() {
        compositeDisposable.dispose()
    }

    fun closeDatabase() {
        database.close()
    }
}