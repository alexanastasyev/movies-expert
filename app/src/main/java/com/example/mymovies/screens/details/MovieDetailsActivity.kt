package com.example.mymovies.screens.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mymovies.data.Movie
import com.example.mymovies.R
import com.example.mymovies.screens.ActivityUtils
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsView {

    companion object {
        const val INVALID_MOVIE_ID = -1
    }

    private lateinit var movie: Movie
    private val presenter = MovieDetailsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        showLoading()
        getIdAndLoadMovieDetails(intent)
        setOnBackArrowExit()
        setOnStarClickListener()
    }

    private fun setOnStarClickListener() {
        findViewById<ImageView>(R.id.movie_details_star).setOnClickListener {
            this.movie.isFavorite = !this.movie.isFavorite
            setStar()
            showStarMessage()
        }
    }

    private fun showStarMessage() {
        if (movie.isFavorite) {
            Toast.makeText(this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMovieInfo(movie: Movie) {
        hideLoading()
        this.movie = movie
        fillFields()
    }

    private fun fillFields() {
        val movieImageView = findViewById<ImageView>(R.id.movie_details_image)
        Picasso.get()
                .load(movie.smallPicturePath)
                .placeholder(R.drawable.movie_big_picture)
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
        Toast.makeText(this, getString(R.string.error_cannot_load_movie_details), Toast.LENGTH_SHORT).show()
    }

    private fun getIdAndLoadMovieDetails(intent: Intent) {
        val movieId = intent.getIntExtra(ActivityUtils.MOVIE_ID_KEY, INVALID_MOVIE_ID)
        if (isIdValid(movieId)) {
            presenter.loadMovieDetails(movieId)
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
        super.onDestroy()
    }
}