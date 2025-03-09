package com.sapan.tmdbapp.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sapan.tmdbapp.R
import com.sapan.tmdbapp.databinding.FragmentHomeScreenBinding

class HomeScreen : Fragment() {

    private lateinit var _binding: FragmentHomeScreenBinding
    private val binding: FragmentHomeScreenBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeScreen()
    }
}