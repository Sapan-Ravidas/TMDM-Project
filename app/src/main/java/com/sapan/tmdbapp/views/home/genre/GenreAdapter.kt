package com.sapan.tmdbapp.views.home.genre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.sapan.tmdbapp.databinding.GenreCardBinding
import com.sapan.tmdbapp.models.GenreData

class GenreAdapter(

): ListAdapter<GenreData, GenreAdapter.GenreViewHolder>(GenreDiffCallback()) {

    class GenreViewHolder(val binding: GenreCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenreData) {
            binding.genreText.text = genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = GenreCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }


    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    class GenreDiffCallback : DiffUtil.ItemCallback<GenreData>() {
        override fun areItemsTheSame(oldItem: GenreData, newItem: GenreData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenreData, newItem: GenreData): Boolean {
            return oldItem == newItem
        }
    }
}