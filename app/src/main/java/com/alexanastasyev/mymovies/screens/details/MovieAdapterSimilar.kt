package com.alexanastasyev.mymovies.screens.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.screens.movies.OnMovieClickListener
import com.squareup.picasso.Picasso

class MovieAdapterSimilar : RecyclerView.Adapter<MovieAdapterSimilar.MovieViewHolder>() {

    val movies: MutableList<Movie> = mutableListOf()
    var onMovieClickListener: OnMovieClickListener? = null

    fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
    }

    class MovieViewHolder(itemView: View, onMovieClickListener: OnMovieClickListener? = null) : RecyclerView.ViewHolder(itemView) {
        var imageViewMoviePicture: ImageView? = null

        init {
            imageViewMoviePicture = itemView.findViewById(R.id.movie_image_similar)

            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_layout_similar, parent, false)
        return MovieViewHolder(itemView, onMovieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Picasso.get()
            .load(movies[position].portraitPicturePath)
            .placeholder(R.drawable.movie_picture_holder)
            .into(holder.imageViewMoviePicture)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}