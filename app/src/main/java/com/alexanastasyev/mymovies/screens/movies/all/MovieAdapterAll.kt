package com.alexanastasyev.mymovies.screens.movies.all

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexanastasyev.mymovies.data.Movie
import com.alexanastasyev.mymovies.R
import com.alexanastasyev.mymovies.screens.movies.OnMovieClickListener
import com.squareup.picasso.Picasso

class MovieAdapterAll : RecyclerView.Adapter<MovieAdapterAll.MovieViewHolder>() {

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

        init {
            imageViewMoviePicture = itemView.findViewById(R.id.movie_image_all)
            textViewMovieTitle = itemView.findViewById(R.id.movie_name)

            itemView.setOnClickListener {
                onMovieClickListener?.onMovieClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_layout_all, parent, false)
        return MovieViewHolder(itemView, onMovieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.textViewMovieTitle?.text = movies[position].title
        Picasso.get()
            .load(movies[position].landscapePicturePath)
            .placeholder(R.drawable.movie_picture_holder)
            .into(holder.imageViewMoviePicture)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}