package com.alexanastasyev.mymovies.screens.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.screens.ActivityUtils
import com.alexanastasyev.mymovies.screens.details.MovieDetailsActivity
import com.alexanastasyev.mymovies.screens.movies.OnMovieClickListener
import com.alexanastasyev.mymovies.screens.movies.all.MovieAdapterAll
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchMoviesFragment : Fragment(), SearchMoviesView {

    companion object {
        private const val SEARCH_DELAY_MILLISECONDS = 500L
    }

    private val presenter = SearchMoviesPresenter(this)

    private lateinit var startingProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val adapter = MovieAdapterAll()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startingProgressBar = view.findViewById(R.id.progress_bar_search)
        recyclerView = view.findViewById(R.id.fragment_recycler_movies_search)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        adapter.onMovieClickListener = object : OnMovieClickListener {
            override fun onMovieClick(position: Int) {
                val movieId = adapter.movies[position].id
                val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                    putExtra(ActivityUtils.MOVIE_ID_KEY, movieId)
                }
                startActivity(intent)
            }
        }
        recyclerView.adapter = adapter

        val editTextSearch = view.findViewById<EditText>(R.id.edit_search)
        addOnEditTextListener(editTextSearch, view.context)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun addOnEditTextListener(editText: EditText, context: Context) {
        val subject = PublishSubject.create<String>()
        editText.addTextChangedListener { str ->
            subject.onNext(str.toString().trim())
        }
        val disposable = subject
            .distinctUntilChanged()
            .debounce(SEARCH_DELAY_MILLISECONDS, TimeUnit.MILLISECONDS)
            .subscribe { str ->
                if (str.isNotEmpty()) {
                    presenter.searchMovie(str, context)
                }
            }
        compositeDisposable.add(disposable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onDestroy() {
        presenter.disposeDisposable()
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun showMovies(movies: List<Movie>) {
        adapter.clear()
        adapter.addMovies(movies)
        adapter.notifyDataSetChanged()
    }

    override fun showError() {
        // Toast.makeText(view?.context, getString(R.string.error_search), Toast.LENGTH_SHORT).show()
        doNothing()
    }

    private fun doNothing() {
        // This function is needed to explicitly show that we don't do anything
    }
}