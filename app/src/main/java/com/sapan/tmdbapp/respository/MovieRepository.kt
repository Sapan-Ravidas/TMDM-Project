package com.sapan.tmdbapp.respository

import android.content.Context
import com.sapan.tmdbapp.lcoal.dao.MovieDao
import com.sapan.tmdbapp.models.GenreData
import com.sapan.tmdbapp.models.GenresResponse
import com.sapan.tmdbapp.models.MovieDataConverter
import com.sapan.tmdbapp.models.local.Bookmark
import com.sapan.tmdbapp.models.remote.MovieListData
import com.sapan.tmdbapp.models.local.Movie
import com.sapan.tmdbapp.network.RemoteService
import com.sapan.tmdbapp.uils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class MovieRepository(
    private val context: Context,
    private val movieDao: MovieDao,
    private val remoteService: RemoteService,
    private val dataConverter: MovieDataConverter,
) {
    fun getGenres(): Flow<List<GenreData>> = flow {
        try {
            val localGenres = movieDao.getAllGenres()
            localGenres.collect { genres ->
                if (genres.isNotEmpty()) {
                    emit(genres)
                } else {
                    val response: Response<GenresResponse> = remoteService.getGenre()
                    if (response.isSuccessful) {
                        val remoteGenres = response.body()?.genres ?: emptyList()
                        movieDao.upsetGenres(remoteGenres)
                        emit(remoteGenres)
                    } else {
                        emit(emptyList())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    /**
     *
     */
    fun getMovies(category: String): Flow<List<Movie>> = flow {
        try {
            val localMovies = movieDao.getMovies(category)
            localMovies.collect { movies ->
                if (movies.isNotEmpty() && !NetworkUtils.isDeviceOnline(context)) {
                    emit(movies)
                } else {
                    val response: Response<MovieListData> = remoteService.getMovies(category)
                    if (response.isSuccessful) {
                        val remoteMovies = dataConverter.mapToDomain(response.body()!!, category)
                        movieDao.deleteMovies(category)
                        movieDao.upsertMovies(remoteMovies)
                        emit(remoteMovies)
                    } else {
                        emit(emptyList())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    /**
     *
     */
    suspend fun bookmarkMovie(movie: Movie) {
        val bookmark = Bookmark(
            id = movie.id,
            backdropPath = movie.backdropPath,
            genreIds = movie.genreIds,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            popularity = movie.popularity,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            title = movie.title,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            video = movie.video
        )
        movieDao.insertBookmark(bookmark)
    }

    suspend fun removeBookmark(movieId: Int) {
        movieDao.deleteBookmark(movieId)
    }


    fun getAllBookmarks(): Flow<List<Bookmark>> {
        return movieDao.getAllBookmarks()
    }

    /**
     *
     */
    fun searchMovies(query: String): Flow<List<Movie>> = flow {
        try {
            val response: Response<MovieListData> = remoteService.searchMovies(query)
            if (response.isSuccessful) {
                val searchResults = response.body()?.results ?: emptyList()
                emit(dataConverter.mapToDomain(response.body()!!, "search"))
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }
}
