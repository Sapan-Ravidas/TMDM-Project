package com.sapan.tmdbapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapan.tmdbapp.R
import com.sapan.tmdbapp.databinding.FragmentHomeScreenBinding
import com.sapan.tmdbapp.models.local.Movie
import com.sapan.tmdbapp.viewmodel.BookmarkViewModel
import com.sapan.tmdbapp.viewmodel.HomeViewModel
import com.sapan.tmdbapp.views.adapter.GenreAdapter
import com.sapan.tmdbapp.views.adapter.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen private constructor(): Fragment() {

    private lateinit var _binding: FragmentHomeScreenBinding
    private val binding: FragmentHomeScreenBinding get() = _binding

    private val homeViewModel: HomeViewModel by viewModels()
    private val bookmarkViewModel: BookmarkViewModel by viewModels()

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var popularMovieAdapter: MovieListAdapter
    private lateinit var nowPlayingMovieAdapter: MovieListAdapter
    private lateinit var upcomingMovieAdapter: MovieListAdapter
    private lateinit var topRatedMovieAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeGenres()
        observeMovies("now_playing", nowPlayingMovieAdapter)
        observeMovies("upcoming", upcomingMovieAdapter)
        observeMovies("popular", popularMovieAdapter)
        observeMovies("top_rated", topRatedMovieAdapter)

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        observeBookmarkedMovies()
    }

    private fun observeBookmarkedMovies() {
        lifecycleScope.launch {
            bookmarkViewModel.getAllBookmarks().collectLatest { bookmarks ->
                val bookmarkedIds = bookmarks.map { it.id }.toSet()
                nowPlayingMovieAdapter.setBookmarkedMovieIds(bookmarkedIds)
                popularMovieAdapter.setBookmarkedMovieIds(bookmarkedIds)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.genreRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        genreAdapter = GenreAdapter()
        binding.genreRecyclerView.adapter = genreAdapter

        binding.nowPlayingMoviesView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingMovieAdapter = getAdapter()
        binding.nowPlayingMoviesView.adapter = nowPlayingMovieAdapter

        binding.upComingMovieView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        upcomingMovieAdapter = getAdapter()
        binding.upComingMovieView.adapter = upcomingMovieAdapter

        binding.popularMoviesView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularMovieAdapter = getAdapter()
        binding.popularMoviesView.adapter = popularMovieAdapter

        binding.topRatedMovieView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topRatedMovieAdapter = getAdapter()
        binding.topRatedMovieView.adapter = topRatedMovieAdapter
    }

    private fun getAdapter() = MovieListAdapter(
        onBookmarkClick = { movie, isBookmarked ->
            lifecycleScope.launch {
                if (isBookmarked) {
                    bookmarkViewModel.bookmarkMovie(movie)
                } else {
                    bookmarkViewModel.removeBookmark(movie.id)
                }
            }
        },
        onItemClick = { movie ->
            navigateToMovieDetails(movie)
        }
    )

    private fun observeGenres() {
        lifecycleScope.launch {
            homeViewModel.genres.collectLatest { genres ->
                genreAdapter.submitList(genres)
            }
        }
    }

    private fun observeMovies(category: String, movieAdapter: MovieListAdapter) {
        lifecycleScope.launch {
            homeViewModel.getMoviesByCategory(category).collectLatest { movies ->
                movieAdapter.submitList(movies)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeScreen()
    }

    private fun navigateToMovieDetails(movie: Movie) {
        val fragment = MovieDetailsScreen.newInstance(movie)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun refreshData() {
        binding.swipeRefreshLayout.isRefreshing = true
        lifecycleScope.launch {
            try {
                homeViewModel.getMoviesByCategory("now_playing").collectLatest { movies ->
                    nowPlayingMovieAdapter.submitList(movies)
                }
                homeViewModel.getMoviesByCategory("popular").collectLatest { movies ->
                    popularMovieAdapter.submitList(movies)
                }
                homeViewModel.genres.collectLatest { genres ->
                    genreAdapter.submitList(genres)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {
                binding.swipeRefreshLayout.isRefreshing = false
            }

        }
    }

}