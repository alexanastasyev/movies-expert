package com.alexanastasyev.mymovies.screens.details

import android.content.Context
import androidx.room.Room
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.database.AppDatabase
import com.alexanastasyev.mymovies.database.DatabaseUtils
import com.alexanastasyev.mymovies.database.converter.DatabaseMovieConverter
import com.alexanastasyev.mymovies.internet.MovieServer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsPresenter(
    private val view: MovieDetailsView,
    context: Context
) {
    private val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DatabaseUtils.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    private val compositeDisposable = CompositeDisposable()

    fun loadMovieDetails(movieId: Int, context: Context) {
        val disposable = Single.fromCallable { database.moviesDao().exist(movieId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieExists ->
                if (movieExists) {
                    loadMovieDetailsFromDatabase(movieId)
                } else {
                    loadMovieDetailsFromServer(movieId, context)
                }
            }, {
                view.showErrorMovieInfo()
            })

        compositeDisposable.add((disposable))
    }

    fun loadSimilarMovies(movieId: Int, context: Context) {
        val disposable = Single.fromCallable { MovieServer.getSimilarMovies(movieId, context) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                if (movies != null) {
                    view.showSimilarMovies(movies)
                } else {
                    view.showErrorSimilarMovies()
                }
            }, {
                view.showErrorSimilarMovies()
            })
        compositeDisposable.add(disposable)
    }

    private fun loadMovieDetailsFromDatabase(movieId: Int) {
        val disposable = Single.fromCallable { database.moviesDao().getById(movieId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ model ->
               if (model != null) {
                   view.showMovieInfo(DatabaseMovieConverter.convertModelsToMovies(listOf(model))[0])
               } else {
                   view.showErrorMovieInfo()
               }
            }, {
                view.showErrorMovieInfo()
            })
        compositeDisposable.add(disposable)
    }

    private fun loadMovieDetailsFromServer(movieId: Int, context: Context) {
        val disposable = Single.fromCallable { MovieServer.getMovieById(movieId, context) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movie ->
                if (movie != null) {
                    view.showMovieInfo(movie)
                } else {
                    view.showErrorMovieInfo()
                }
            }, {
                view.showErrorMovieInfo()
            })
        compositeDisposable.add(disposable)
    }

    fun addMovieToDatabase(movie: Movie) {
        val disposable = Single.fromCallable {
            database.moviesDao().insert(DatabaseMovieConverter.convertMoviesToModels(listOf(movie))[0])
        }.subscribeOn(Schedulers.io()).subscribe()
        compositeDisposable.add(disposable)
    }

    fun removeMovieFromDatabase(movie: Movie) {
        val disposable = Single.fromCallable {
            database.moviesDao().delete(DatabaseMovieConverter.convertMoviesToModels(listOf(movie))[0])
        }.subscribeOn(Schedulers.io()).subscribe()
        compositeDisposable.add(disposable)
    }

    fun disposeDisposable() {
        compositeDisposable.dispose()
    }

    fun closeDatabase() {
        database.close()
    }
}