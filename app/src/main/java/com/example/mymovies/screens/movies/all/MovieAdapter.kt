package com.example.mymovies.screens.movies.all

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.Movie
import com.example.mymovies.R
import com.example.mymovies.internet.NetworkUtils
import com.squareup.picasso.Picasso

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewMoviePicture: ImageView? = null
        var textViewMovieTitle: TextView? = null

        init {
            imageViewMoviePicture = itemView.findViewById(R.id.movie_image)
            textViewMovieTitle = itemView.findViewById(R.id.movie_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_layout, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.textViewMovieTitle?.text = movies[position].title
        Picasso.get()
            .load(NetworkUtils.IMAGE_BASE_URL + NetworkUtils.IMAGE_BIG_SIZE + movies[position].bigPicturePath)
            .placeholder(R.drawable.movie_big_picture)
            .into(holder.imageViewMoviePicture)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}