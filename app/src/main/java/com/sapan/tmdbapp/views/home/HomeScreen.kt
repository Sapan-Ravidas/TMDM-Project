package com.sapan.tmdbapp.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapan.tmdbapp.R
import com.sapan.tmdbapp.databinding.FragmentHomeScreenBinding
import com.sapan.tmdbapp.viewmodel.HomeViewModel
import com.sapan.tmdbapp.views.home.genre.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen : Fragment() {

    private lateinit var _binding: FragmentHomeScreenBinding
    private val binding: FragmentHomeScreenBinding get() = _binding

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var genreAdapter: GenreAdapter


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
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.genreRecyclerView.layoutManager = layoutManager
        genreAdapter = GenreAdapter()
        binding.genreRecyclerView.adapter = genreAdapter
    }

    private fun observeGenres() {
        lifecycleScope.launch {
            viewModel.genres.collectLatest { genres ->
                genreAdapter.submitList(genres)
            }
        }
    }

    private fun observeMovies(category: String) {
        lifecycleScope.launch {
            viewModel.getMoviesByCategory(category).collectLatest { movies ->
                // movieAdapter.submitList(movies)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeScreen()
    }
}