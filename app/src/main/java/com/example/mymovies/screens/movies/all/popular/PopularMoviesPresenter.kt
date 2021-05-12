package com.example.mymovies.screens.movies.all.popular

import android.util.Log
import com.example.mymovies.internet.MovieServer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopularMoviesPresenter(private val view: PopularMoviesView) {

    companion object {
        private const val MAX_PAGE = 1000
    }

    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 0

    private var isLoading = false

    fun loadNextPage() {
        if (isLoading) {
            return
        }
        isLoading = true
        currentPage++
        if (currentPage >= MAX_PAGE) {
            view.showError()
            return
        }
        val disposable = Single.fromCallable { MovieServer.getPopularMovies(currentPage) }
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