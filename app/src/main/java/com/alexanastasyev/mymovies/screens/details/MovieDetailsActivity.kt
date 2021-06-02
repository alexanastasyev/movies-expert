package com.alexanastasyev.mymovies.screens.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.screens.ActivityUtils
import com.alexanastasyev.mymovies.screens.movies.OnMovieClickListener
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsView {

    companion object {
        const val INVALID_MOVIE_ID = -1
    }

    private lateinit var movie: Movie
    private lateinit var presenter: MovieDetailsPresenter
    private lateinit var recyclerView: RecyclerView
    private val adapter = MovieAdapterSimilar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        recyclerView = findViewById(R.id.recycler_view_similar_movies)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter.onMovieClickListener = object : OnMovieClickListener {
            override fun onMovieClick(position: Int) {
                val movieId = adapter.movies[position].id
                val intent = Intent(applicationContext, MovieDetailsActivity::class.java).apply {
                    putExtra(ActivityUtils.MOVIE_ID_KEY, movieId)
                }
                startActivity(intent)
            }
        }
        recyclerView.adapter = adapter

        presenter = MovieDetailsPresenter(this, this)

        showLoading()
        showLoadingSimilar()
        getIdAndLoadMovieDetails(intent)
        setOnBackArrowExit()
        setOnStarClickListener()
    }

    private fun showLoadingSimilar() {
        findViewById<ProgressBar>(R.id.similar_movies_progress_bar).visibility = View.VISIBLE
    }

    private fun setOnStarClickListener() {
        findViewById<ImageView>(R.id.movie_details_star).setOnClickListener {
            @Suppress("SENSELESS_COMPARISON")
            if (this.movie != null) {
                this.movie.isFavorite = !this.movie.isFavorite
                if (movie.isFavorite) {
                    presenter.addMovieToDatabase(movie)
                } else {
                    presenter.removeMovieFromDatabase(movie)
                }
                setStar()
                showStarMessage()
            }
        }
    }

    private fun showStarMessage() {
        if (movie.isFavorite) {
            showMessage(getString(R.string.added_to_favorites))
        } else {
            showMessage(getString(R.string.removed_from_favorites))
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMovieInfo(movie: Movie) {
        hideLoading()
        this.movie = movie
        fillFields()
    }

    override fun showSimilarMovies(movies: List<Movie>) {
        adapter.movies.clear()
        adapter.addMovies(movies)
        adapter.notifyDataSetChanged()
        hideLoadingSimilar()
    }

    private fun hideLoadingSimilar() {
        findViewById<ProgressBar>(R.id.similar_movies_progress_bar).visibility = View.INVISIBLE
    }

    private fun fillFields() {
        val movieImageView = findViewById<ImageView>(R.id.movie_details_image)
        Picasso.get()
            .load(movie.portraitPicturePath)
            .placeholder(R.drawable.movie_picture_holder)
            .into(movieImageView)
        findViewById<TextView>(R.id.movie_details_title_edit).text = movie.title
        findViewById<TextView>(R.id.movie_details_date_edit).text = movie.date
        findViewById<TextView>(R.id.movie_details_rating_edit).text = movie.rating.toString()
        findViewById<TextView>(R.id.movie_details_description).text = movie.description
        findViewById<TextView>(R.id.movie_details_genres_edit).text = movie.genres
        setStar()
    }

    private fun setStar() {
        if (movie.isFavorite) {
            setStarFavorite()
        } else {
            setStarNotFavorite()
        }
    }

    private fun setStarFavorite() {
        findViewById<ImageView>(R.id.movie_details_star).setImageResource(R.drawable.star_favorite)
    }

    private fun setStarNotFavorite() {
        findViewById<ImageView>(R.id.movie_details_star).setImageResource(R.drawable.star_not_favorite)
    }

    override fun showErrorMovieInfo() {
        showLoading()
        showMessage(getString(R.string.error_cannot_load_movie_details))
    }

    override fun showErrorSimilarMovies() {
        showMessage(getString(R.string.error_loading_similar_movies))
    }

    private fun getIdAndLoadMovieDetails(intent: Intent) {
        val movieId = intent.getIntExtra(ActivityUtils.MOVIE_ID_KEY, INVALID_MOVIE_ID)
        if (isIdValid(movieId)) {
            presenter.loadMovieDetails(movieId, this)
            presenter.loadSimilarMovies(movieId, this)
        } else {
            showErrorMovieInfo()
        }
    }

    private fun isIdValid(id: Int): Boolean {
        return id != INVALID_MOVIE_ID
    }

    private fun setOnBackArrowExit() {
        findViewById<ImageView>(R.id.movie_details_back).setOnClickListener {
            this.finish()
        }
    }

    private fun showLoading() {
        hideMovieLayout()
        showProgressBar()
    }

    private fun hideLoading() {
        hideProgressBar()
        showMovieLayout()
    }

    private fun showProgressBar() {
        findViewById<ProgressBar>(R.id.movie_details_progress_bar).visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        findViewById<ProgressBar>(R.id.movie_details_progress_bar).visibility = View.GONE
    }

    private fun showMovieLayout() {
        findViewById<ConstraintLayout>(R.id.movie_details_layout).visibility = View.VISIBLE
    }

    private fun hideMovieLayout() {
        findViewById<ConstraintLayout>(R.id.movie_details_layout).visibility = View.GONE
    }

    override fun onDestroy() {
        presenter.disposeDisposable()
        presenter.closeDatabase()
        super.onDestroy()
    }
}