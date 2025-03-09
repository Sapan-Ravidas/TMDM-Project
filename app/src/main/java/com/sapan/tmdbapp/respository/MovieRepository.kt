package com.sapan.tmdbapp.respository

import android.content.Context
import com.sapan.tmdbapp.lcoal.dao.MovieDao
import com.sapan.tmdbapp.models.DataConverter
import com.sapan.tmdbapp.models.GenreData
import com.sapan.tmdbapp.models.GenresResponse
import com.sapan.tmdbapp.models.MovieDataConverter
import com.sapan.tmdbapp.models.MovieListData
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
    }

    /**
     *
     */
    fun getMovies(category: String): Flow<List<Movie>> = flow {
        val localMovies = movieDao.getMovies(category)
        localMovies.collect { movies ->
            if (movies.isNotEmpty() && !NetworkUtils.isDeviceOnline(context)) {
                emit(movies)
            } else {
                val response: Response<MovieListData> = remoteService.getMovies()
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
    }

    /**
     *
     */
}
