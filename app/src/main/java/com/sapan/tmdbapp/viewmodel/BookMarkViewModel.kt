package com.sapan.tmdbapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapan.tmdbapp.models.local.Bookmark
import com.sapan.tmdbapp.models.local.Movie
import com.sapan.tmdbapp.respository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun getAllBookmarks(): Flow<List<Bookmark>> {
        return movieRepository.getAllBookmarks()
    }

    fun bookmarkMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.bookmarkMovie(movie)
        }
    }

    fun removeBookmark(movieId: Int) {
        viewModelScope.launch {
            movieRepository.removeBookmark(movieId)
        }
    }

}