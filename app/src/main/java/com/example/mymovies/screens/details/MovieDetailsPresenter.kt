package com.example.mymovies.screens.details

import android.util.Log
import com.example.mymovies.internet.MovieServer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsPresenter(private val view: MovieDetailsView) {

    private val compositeDisposable = CompositeDisposable()

    fun loadMovieDetails(movieId: Int) {
        val disposable = Single.fromCallable { MovieServer.getMovieById(movieId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movie ->
                Log.i("XELAGUB", "Presenter received movie")
               if (movie != null) {
                   view.showMovieInfo(movie)
               } else {
                   view.showError()
               }
            }, {
                Log.i("XELAGUB", "RXJava error: " + it.message)
                view.showError()
            })
        compositeDisposable.add(disposable)
    }

    fun disposeDisposable() {
        compositeDisposable.dispose()
    }
}