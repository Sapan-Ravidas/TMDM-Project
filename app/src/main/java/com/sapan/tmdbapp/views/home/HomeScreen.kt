package com.sapan.tmdbapp.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sapan.tmdbapp.R
import com.sapan.tmdbapp.databinding.FragmentHomeScreenBinding
import com.sapan.tmdbapp.viewmodel.GenreViewModel
import com.sapan.tmdbapp.views.home.genre.GenreAdapter

class HomeScreen : Fragment() {

    private lateinit var _binding: FragmentHomeScreenBinding
    private val binding: FragmentHomeScreenBinding get() = _binding

    private val viewModel: GenreViewModel by viewModels()
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeScreen()
    }
}