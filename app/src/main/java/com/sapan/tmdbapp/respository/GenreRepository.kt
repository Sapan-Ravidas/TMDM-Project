package com.sapan.tmdbapp.respository

import com.sapan.tmdbapp.lcoal.dao.GenreDao
import com.sapan.tmdbapp.models.GenreData
import com.sapan.tmdbapp.models.GenresResponse
import com.sapan.tmdbapp.network.RemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class GenreRepository(
    private val genreDao: GenreDao,
    private val remoteService: RemoteService
) {
    fun getGenres(): Flow<List<GenreData>> = flow {
        val localGenres = genreDao.getAllGenres()
        localGenres.collect { genres ->
            if (genres.isNotEmpty()) {
                emit(genres)
            } else {
                val response: Response<GenresResponse> = remoteService.getGenre()
                if (response.isSuccessful) {
                    val remoteGenres = response.body()?.genres ?: emptyList()
                    genreDao.upsetGenres(remoteGenres)
                    emit(remoteGenres)
                } else {
                    emit(emptyList())
                }
            }
        }
    }
}
