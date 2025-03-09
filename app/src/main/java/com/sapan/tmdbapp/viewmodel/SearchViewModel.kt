package com.sapan.tmdbapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapan.tmdbapp.models.MovieDataConverter
import com.sapan.tmdbapp.models.local.Bookmark
import com.sapan.tmdbapp.models.local.Movie
import com.sapan.tmdbapp.respository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val movieDataConverter: MovieDataConverter
)  : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _searchResults = MutableStateFlow<List<Bookmark>>(emptyList())
    val searchResults: StateFlow<List<Bookmark>> get() = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        // Debounce the search query and trigger the search
        viewModelScope.launch {
            _searchQuery
                .debounce(300) // Debounce for 300ms
                .flatMapLatest { query ->
                    if (query.isNotEmpty()) {
                        _isLoading.value = true
                        repository.searchMovies(query)
                            .map { movies ->
                                movies.map { movie ->
                                    movieDataConverter.mapToBookmark(movie)
                                }
                            }
                    } else {
                        flow { emit(emptyList<Bookmark>()) }
                    }
                }
                .collect { bookmarks ->
                    _searchResults.value = bookmarks
                    _isLoading.value = false
                }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun bookmarkMovie(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.bookmarkMovie(
                Movie(
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
                    category = "search" // Assuming a default category for search results
                )
            )
        }
    }

    fun removeBookmark(movieId: Int) {
        viewModelScope.launch {
            repository.removeBookmark(movieId)
        }
    }

}