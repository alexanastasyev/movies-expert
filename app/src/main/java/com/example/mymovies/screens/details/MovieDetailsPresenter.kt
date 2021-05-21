package com.example.mymovies.screens.details

import com.example.mymovies.data.Movie
import com.example.mymovies.database.Database
import com.example.mymovies.database.DatabaseMovieConverter
import com.example.mymovies.internet.MovieServer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsPresenter(
    private val view: MovieDetailsView
) {

    private val database = Database.get()
    private val compositeDisposable = CompositeDisposable()

    fun loadMovieDetails(movieId: Int) {

        val disposable = Single.fromCallable { database.moviesDao().exist(movieId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieExists ->
                if (movieExists) {
                    loadMovieDetailsFromDatabase(movieId)
                } else {
                    loadMovieDetailsFromServer(movieId)
                }
            }, {
                view.showError()
            })

        compositeDisposable.add((disposable))
    }

    private fun loadMovieDetailsFromDatabase(movieId: Int) {
        val disposable = Single.fromCallable { database.moviesDao().getById(movieId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ model ->
               if (model != null) {
                   view.showMovieInfo(DatabaseMovieConverter.convertModelsToMovies(listOf(model))[0])
               } else {
                   view.showError()
               }
            }, {
                view.showError()
            })
        compositeDisposable.add(disposable)
    }

    private fun loadMovieDetailsFromServer(movieId: Int) {
        val disposable = Single.fromCallable { MovieServer.getMovieById(movieId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movie ->
                if (movie != null) {
                    view.showMovieInfo(movie)
                } else {
                    view.showError()
                }
            }, {
                view.showError()
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
}