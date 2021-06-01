package com.alexanastasyev.mymovies.screens.search

import android.content.Context
import com.alexanastasyev.mymovies.internet.MovieServer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchMoviesPresenter(private val view: SearchMoviesView) {
    private val compositeDisposable = CompositeDisposable()
    private var isLoading = false

    fun searchMovie(query: String, context: Context) {
        if (isLoading) {
            return
        }
        isLoading = true
        val disposable = Single.fromCallable { MovieServer.searchMovie(query, context) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                if (movies != null) {
                    view.showMovies(movies)
                } else {
                    view.showError()
                }
                isLoading = false
            }, {
                view.showError()
                isLoading = false
            })
        compositeDisposable.add(disposable)
    }

    fun disposeDisposable() {
        compositeDisposable.dispose()
    }
}