package com.sapan.tmdbapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapan.tmdbapp.databinding.FragmentHomeScreenBinding
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

    private val viewModel: HomeViewModel by viewModels()
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
        observeMovies("popular", popularMovieAdapter)
    }

    private fun setupRecyclerView() {
        binding.genreRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        genreAdapter = GenreAdapter()
        binding.genreRecyclerView.adapter = genreAdapter

        binding.nowPlayingMoviesView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingMovieAdapter = MovieListAdapter { movie ->
            // TODO: Handle bookmark click
        }
        binding.nowPlayingMoviesView.adapter = nowPlayingMovieAdapter

        binding.popularMoviesView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularMovieAdapter = MovieListAdapter { movie ->
            // TODO: Handle bookmark click
        }
        binding.popularMoviesView.adapter = popularMovieAdapter
    }

    private fun observeGenres() {
        lifecycleScope.launch {
            viewModel.genres.collectLatest { genres ->
                genreAdapter.submitList(genres)
            }
        }
    }

    private fun observeMovies(category: String, movieAdapter: MovieListAdapter) {
        lifecycleScope.launch {
            viewModel.getMoviesByCategory(category).collectLatest { movies ->
                movieAdapter.submitList(movies)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeScreen()
    }
}