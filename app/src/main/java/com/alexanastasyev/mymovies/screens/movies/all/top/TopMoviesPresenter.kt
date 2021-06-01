package com.alexanastasyev.mymovies.screens.movies.all.top

import android.content.Context
import com.alexanastasyev.mymovies.internet.MovieServer
import com.alexanastasyev.mymovies.screens.movies.all.MoviesView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TopMoviesPresenter(private val view: MoviesView) {

    companion object {
        private const val MAX_PAGE = 1000
    }

    private val compositeDisposable = CompositeDisposable()
    private var lastPageIndex = 0

    private var isLoading = false

    fun loadNextPage(context: Context) {
        if (isLoading) {
            return
        }
        isLoading = true
        lastPageIndex++
        if (lastPageIndex >= MAX_PAGE) {
            isLoading = false
            view.showError()
            return
        }
        val disposable = Single.fromCallable { MovieServer.getTopMovies(lastPageIndex, context) }
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