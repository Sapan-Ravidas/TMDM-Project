package com.sapan.tmdbapp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sapan.tmdbapp.BuildConfig
import com.sapan.tmdbapp.R
import com.sapan.tmdbapp.databinding.BookmarkItemBinding
import com.sapan.tmdbapp.models.local.Bookmark
import com.sapan.tmdbapp.network.ApiConstants

class BookmarkAdapter(
    private val onItemClick: (Bookmark) -> Unit
): ListAdapter<Bookmark, BookmarkAdapter.BookmarkViewHolder>(BookmarkDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = BookmarkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = getItem(position)
        holder.bind(bookmark)
        holder.itemView.setOnClickListener {
            onItemClick(bookmark)
        }
    }

    inner class BookmarkViewHolder(private val binding: BookmarkItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmark: Bookmark) {
            binding.moviePoster.load("${BuildConfig.BASE_POSTER_PATH}${bookmark.posterPath}") {
                crossfade(true)
                placeholder(R.drawable.img_paceholder)
                error(R.drawable.img_error)
            }

            binding.movieTitle.text = bookmark.title
            binding.movieRating.text = bookmark.voteAverage.toString()
            binding.movieOverview.text = bookmark.overview
            binding.movieReleaseDate.text = bookmark.releaseDate
        }
    }

    class BookmarkDiffCallback : DiffUtil.ItemCallback<Bookmark>() {
        override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
            return oldItem == newItem
        }
    }
}