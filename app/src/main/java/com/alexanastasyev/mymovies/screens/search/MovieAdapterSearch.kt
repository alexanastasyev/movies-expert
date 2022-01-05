package com.alexanastasyev.mymovies.screens.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.data.MoviesUtils
import com.alexanastasyev.mymovies.data.MoviesUtils.AWFUL_RATING
import com.alexanastasyev.mymovies.data.MoviesUtils.DATE_YEAR_INDEX
import com.alexanastasyev.mymovies.data.MoviesUtils.HIGH_RATING
import com.alexanastasyev.mymovies.data.MoviesUtils.LOW_RATING
import com.alexanastasyev.mymovies.data.MoviesUtils.MEDIUM_RATING
import com.alexanastasyev.mymovies.data.MoviesUtils.ULTRA_RATING
import com.alexanastasyev.mymovies.data.MoviesUtils.ZERO_RATING
import com.alexanastasyev.mymovies.internet.server.NetworkUtils
import com.alexanastasyev.mymovies.screens.movies.OnMovieClickListener
import com.squareup.picasso.Picasso

class MovieAdapterSearch(private val context: Context) : RecyclerView.Adapter<MovieAdapterSearch.MovieViewHolder>() {

    val movies: MutableList<Movie> = mutableListOf()
    var onMovieClickListener: OnMovieClickListener? = null

    fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
    }

    fun clear() {
        movies.clear()
    }

    class MovieViewHolder(itemView: View, onMovieClickListener: OnMovieClickListener? = null) : RecyclerView.ViewHolder(itemView) {
        var imageViewMoviePicture: ImageView? = null
        var textViewMovieTitle: TextView? = null
        var textViewMovieYear: TextView? = null
        var textViewMovieGenres: TextView? = null
        var textViewMovieRating: TextView? = null

        init {
            imageViewMoviePicture = itemView.findViewById(R.id.movie_image_search)
            textViewMovieTitle = itemView.findViewById(R.id.movie_name_search)
            textViewMovieYear = itemView.findViewById(R.id.movie_year_search)
            textViewMovieGenres = itemView.findViewById(R.id.movie_genres_search)
            textViewMovieRating = itemView.findViewById(R.id.text_view_rating)

            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_layout_search, parent, false)
        return MovieViewHolder(itemView, onMovieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.textViewMovieTitle?.text = movies[position].title
        val year = if (movies[position].date.isNotEmpty()) {
            movies[position].date.split(NetworkUtils.DATE_DELIMITER)[DATE_YEAR_INDEX]
        } else {
            ""
        }
        holder.textViewMovieYear?.text = year
        holder.textViewMovieGenres?.text = movies[position].genres
        when (movies[position].rating) {
            ZERO_RATING -> holder.textViewMovieRating?.setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
            in AWFUL_RATING -> holder.textViewMovieRating?.setTextColor(ContextCompat.getColor(context, R.color.rating_awful))
            in LOW_RATING -> holder.textViewMovieRating?.setTextColor(ContextCompat.getColor(context, R.color.rating_low))
            in MEDIUM_RATING -> holder.textViewMovieRating?.setTextColor(ContextCompat.getColor(context, R.color.rating_medium))
            in HIGH_RATING -> holder.textViewMovieRating?.setTextColor(ContextCompat.getColor(context, R.color.rating_high))
            in ULTRA_RATING -> holder.textViewMovieRating?.setTextColor(ContextCompat.getColor(context, R.color.rating_ultra))
        }
        if (movies[position].rating == 0) {
            holder.textViewMovieRating?.text = MoviesUtils.RATING_PLACEHOLDER
        } else {
            holder.textViewMovieRating?.text = movies[position].rating.toString()
        }
        Picasso.get()
            .load(movies[position].portraitPicturePath)
            .placeholder(R.drawable.movie_picture_holder)
            .into(holder.imageViewMoviePicture)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}