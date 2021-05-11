package com.example.mymovies.screens.movies.all.top

import com.example.mymovies.internet.MovieService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TopMoviesPresenter(private val view: TopMoviesView) {

    private val compositeDisposable = CompositeDisposable()

    fun loadTopMovies() {
        val disposable = Single.fromCallable { MovieService.getTopMovies() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    if (movies != null) {
                        view.showMovies(movies)
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
}