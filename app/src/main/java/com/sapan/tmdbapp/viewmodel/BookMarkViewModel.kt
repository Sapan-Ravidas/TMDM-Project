package com.sapan.tmdbapp.viewmodel

import androidx.lifecycle.ViewModel
import com.sapan.tmdbapp.models.local.Bookmark
import com.sapan.tmdbapp.respository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun getAllBookmarks(): Flow<List<Bookmark>> {
        return movieRepository.getAllBookmarks()
    }
}