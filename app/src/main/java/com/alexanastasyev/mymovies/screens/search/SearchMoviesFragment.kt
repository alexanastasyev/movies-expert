package com.alexanastasyev.mymovies.screens.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.internet.NetworkUtils
import com.alexanastasyev.mymovies.screens.ActivityUtils
import com.alexanastasyev.mymovies.screens.details.MovieDetailsActivity
import com.alexanastasyev.mymovies.screens.movies.OnMovieClickListener
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchMoviesFragment : Fragment(), SearchMoviesView {

    companion object {
        const val PAGINATION_NUMBER = 5
    }

    private val presenter = SearchMoviesPresenter(this)

    private lateinit var startingProgressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewNotFound: TextView
    private lateinit var adapter: MovieAdapterSearch

    private var paginationEnabled = true

    private var currentQuery = ""

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MovieAdapterSearch(view.context)

        startingProgressBar = view.findViewById(R.id.progress_bar_search)
        recyclerView = view.findViewById(R.id.fragment_recycler_movies_search)
        textViewNotFound = view.findViewById(R.id.text_view_movie_not_found)
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

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (paginationEnabled) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalItemCount = recyclerView.layoutManager?.itemCount
                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                    if (totalItemCount != null) {
                        if (totalItemCount - lastVisibleItemPosition <= PAGINATION_NUMBER) {
                            presenter.loadNextPage(requireContext())
                        }

                        if (lastVisibleItemPosition == totalItemCount - 1) {
                            startingProgressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun addOnEditTextListener(editText: EditText, context: Context) {
        val subject = PublishSubject.create<String>()
        editText.addTextChangedListener { str ->
            val query = str.toString().trim()
            if (query.isNullOrEmpty()) {
                hideAll()
            } else {
                if (query != currentQuery) {
                    showLoading()
                    textViewNotFound.visibility = View.GONE
                    subject.onNext(query)
                    currentQuery = query
                }
            }
        }
        val disposable = subject
            .distinctUntilChanged()
            .debounce(NetworkUtils.SEARCH_DELAY_MILLISECONDS, TimeUnit.MILLISECONDS)
            .subscribe { str ->
                if (str.isNotEmpty()) {
                    presenter.searchNewMovie(str, context)
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

    override fun showNewMovies(movies: List<Movie>) {
        hideLoading()
        recyclerView.scrollToPosition(0)
        adapter.clear()
        adapter.addMovies(movies)
        adapter.notifyDataSetChanged()

        if (movies.isEmpty()) {
            textViewNotFound.visibility = View.VISIBLE
        }
    }

    override fun addMovies(movies: List<Movie>) {
        hideLoading()
        adapter.addMovies(movies)
        adapter.notifyDataSetChanged()
    }

    override fun stopPagination() {
        paginationEnabled = false
    }

    override fun resumePagination() {
        paginationEnabled = true
    }

    override fun showError() {
        Snackbar.make(recyclerView, getString(R.string.error_search), Snackbar.LENGTH_SHORT).show()
        startingProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        textViewNotFound.visibility = View.GONE
        recyclerView.visibility = View.GONE
        startingProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        recyclerView.visibility = View.VISIBLE
        startingProgressBar.visibility = View.GONE
    }

    private fun hideAll() {
        recyclerView.visibility = View.GONE
        startingProgressBar.visibility = View.GONE
    }

}