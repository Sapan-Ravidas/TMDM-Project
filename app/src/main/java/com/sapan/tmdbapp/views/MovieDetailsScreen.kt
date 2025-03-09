package com.sapan.tmdbapp.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.sapan.tmdbapp.BuildConfig
import com.sapan.tmdbapp.R
import com.sapan.tmdbapp.databinding.FragmentDetailsBinding
import com.sapan.tmdbapp.models.local.Movie
import com.sapan.tmdbapp.network.ApiConstants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsScreen: Fragment() {

    private lateinit var _binding: FragmentDetailsBinding
    private val binding: FragmentDetailsBinding get() = _binding

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getParcelable<Movie>(ARG_MOVIE) ?: throw IllegalArgumentException("Movie argument is required")
        setupMovieDetails()

        binding.shareButton.setOnClickListener {
            shareMovie()
        }
    }

    private fun setupMovieDetails() {
        binding.bannerImage.load("${BuildConfig.BASE_BACKDROP_PATH}${movie?.backdropPath}") {
            crossfade(true)
            placeholder(R.drawable.img_paceholder)
            error(R.drawable.img_error)
        }

        binding.posterImage.load("${BuildConfig.BASE_POSTER_PATH}${movie?.posterPath}") {
            crossfade(true)
            placeholder(R.drawable.img_paceholder)
            error(R.drawable.img_error)
        }

        binding.movieTitle.text = movie?.title
        binding.rating.text = movie?.voteAverage.toString()
        binding.overview.text = movie?.overview
    }


    private fun shareMovie() {
        val shareText = "Check out this movie: ${movie?.title}\nhttps://www.example.com/movie/${movie?.id}"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    companion object {
        private const val ARG_MOVIE = "movie"

        fun newInstance(movie: Movie): MovieDetailsScreen {
            val fragment = MovieDetailsScreen()
            val args = Bundle().apply {
                putParcelable(ARG_MOVIE, movie)
            }
            fragment.arguments = args
            return fragment
        }
    }
}