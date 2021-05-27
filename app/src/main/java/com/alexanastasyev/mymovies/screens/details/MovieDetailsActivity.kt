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
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.screens.ActivityUtils
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsView {

    companion object {
        const val INVALID_MOVIE_ID = -1
    }

    private lateinit var movie: Movie
    private lateinit var presenter: MovieDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        presenter = MovieDetailsPresenter(this, this)

        showLoading()
        getIdAndLoadMovieDetails(intent)
        setOnBackArrowExit()
        setOnStarClickListener()
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

    override fun showError() {
        showLoading()
        showMessage(getString(R.string.error_cannot_load_movie_details))
    }

    private fun getIdAndLoadMovieDetails(intent: Intent) {
        val movieId = intent.getIntExtra(ActivityUtils.MOVIE_ID_KEY, INVALID_MOVIE_ID)
        if (isIdValid(movieId)) {
            presenter.loadMovieDetails(movieId, this)
        } else {
            showError()
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