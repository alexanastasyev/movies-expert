package com.example.mymovies.screens.movies.all

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.data.Movie
import com.example.mymovies.R
import com.squareup.picasso.Picasso

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val movies: MutableList<Movie> = mutableListOf()
    var onMovieClickListener: OnMovieClickListener? = null

    fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
    }

    class MovieViewHolder(itemView: View, onMovieClickListener: OnMovieClickListener? = null) : RecyclerView.ViewHolder(itemView) {
        var imageViewMoviePicture: ImageView? = null
        var textViewMovieTitle: TextView? = null

        init {
            imageViewMoviePicture = itemView.findViewById(R.id.movie_image)
            textViewMovieTitle = itemView.findViewById(R.id.movie_name)

            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClick(adapterPosition)
            }
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_layout, parent, false)
        return MovieViewHolder(itemView, onMovieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.textViewMovieTitle?.text = movies[position].title
        Picasso.get()
            .load(movies[position].bigPicturePath)
            .placeholder(R.drawable.movie_big_picture)
            .into(holder.imageViewMoviePicture)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}