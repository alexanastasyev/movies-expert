package com.example.mymovies.screens.movies.all.popular

import com.example.mymovies.internet.MovieService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopularMoviesPresenter(private val view: PopularMoviesView) {

    private val compositeDisposable = CompositeDisposable()

    fun loadPopularMovies() {
        val disposable = Single.fromCallable { MovieService.getPopularMovies() }
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