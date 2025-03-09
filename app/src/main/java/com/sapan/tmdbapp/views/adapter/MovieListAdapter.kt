package com.sapan.tmdbapp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sapan.tmdbapp.R
import com.sapan.tmdbapp.databinding.CardMovieBinding
import com.sapan.tmdbapp.models.local.Movie
import com.sapan.tmdbapp.network.ApiConstants

class MovieListAdapter(
    private val onBookmarkClick: (Movie, Boolean) -> Unit
) : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = CardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class MovieViewHolder(private val binding: CardMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.moviePoster.load("${ApiConstants.BASE_POSTER_PATH}${movie.posterPath}") {
                crossfade(true)
                placeholder(R.drawable.img_paceholder)
                error(R.drawable.img_error)
            }

            binding.rating.text = movie.voteAverage.toString()
            val isBookmarked = false
            binding.bookmark.setOnClickListener {
                val newBookmarkState = !isBookmarked
                updateBookmarkButton(newBookmarkState)
                onBookmarkClick(movie, newBookmarkState)
            }
        }

        private fun updateBookmarkButton(isBookmarked: Boolean) {
            if (isBookmarked) {
                binding.bookmark.setColorFilter(itemView.context.getColor(R.color.red))
                binding.bookmark.elevation = 8f
            } else {
                binding.bookmark.setColorFilter(itemView.context.getColor(R.color.white))
                binding.bookmark.elevation = 0f
            }
        }
    }


    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}