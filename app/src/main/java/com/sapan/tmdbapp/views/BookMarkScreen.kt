package com.sapan.tmdbapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapan.tmdbapp.R
import com.sapan.tmdbapp.databinding.FragmentBookmarkBinding
import com.sapan.tmdbapp.models.local.Movie
import com.sapan.tmdbapp.viewmodel.BookmarkViewModel
import com.sapan.tmdbapp.views.adapter.BookmarkAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookMarkScreen private constructor(): Fragment() {

    private lateinit var _binding: FragmentBookmarkBinding
    private val binding: FragmentBookmarkBinding get() = _binding

    private val viewModel: BookmarkViewModel by viewModels()
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeBookmarks()
    }

    private fun setupRecyclerView() {
        binding.bookmarkRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookmarkAdapter = BookmarkAdapter { bookmark ->
            val movie = Movie(
                id = bookmark.id,
                backdropPath = bookmark.backdropPath,
                genreIds = bookmark.genreIds,
                originalLanguage = bookmark.originalLanguage,
                originalTitle = bookmark.originalTitle,
                overview = bookmark.overview,
                popularity = bookmark.popularity,
                posterPath = bookmark.posterPath,
                releaseDate = bookmark.releaseDate,
                title = bookmark.title,
                voteAverage = bookmark.voteAverage,
                voteCount = bookmark.voteCount,
                video = bookmark.video,
                category = "bookmark"
            )
            navigateToMovieDetails(movie)
        }
        binding.bookmarkRecyclerView.adapter = bookmarkAdapter
    }

    private fun observeBookmarks() {
        lifecycleScope.launch {
            viewModel.getAllBookmarks().collectLatest { bookmarks ->
                bookmarkAdapter.submitList(bookmarks)
            }
        }
    }

    private fun navigateToMovieDetails(movie: Movie) {
        val fragment = MovieDetailsScreen.newInstance(movie)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() : BookMarkScreen = BookMarkScreen()
    }
}