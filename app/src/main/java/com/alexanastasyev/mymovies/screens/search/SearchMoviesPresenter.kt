package com.alexanastasyev.mymovies.screens.search

import android.content.Context
import com.alexanastasyev.mymovies.internet.server.MovieServer
import com.alexanastasyev.mymovies.internet.server.NetworkUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchMoviesPresenter(private val view: SearchMoviesView) {

    private val compositeDisposable = CompositeDisposable()
    private var isLoading = false
    private var lastPageIndex = 0
    private var currentQuery = ""

    fun searchNewMovie(query: String, context: Context) {
        if (isLoading) {
            return
        }
        isLoading = true
        currentQuery = query
        lastPageIndex = 1
        view.resumePagination()
        val disposable = Single.fromCallable { MovieServer.searchMovie(currentQuery, lastPageIndex, context) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                if (movies != null) {
                    view.showNewMovies(movies)
                    if (movies.size < NetworkUtils.MOVIES_PER_PAGE) {
                        view.stopPagination()
                    }
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

    fun loadNextPage(context: Context) {
        if (isLoading) {
            return
        }
        isLoading = true
        lastPageIndex++
        val disposable = Single.fromCallable { MovieServer.searchMovie(currentQuery, lastPageIndex, context) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                if (movies != null) {
                    view.addMovies(movies)
                    if (movies.size < NetworkUtils.MOVIES_PER_PAGE) {
                        view.stopPagination()
                    }
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