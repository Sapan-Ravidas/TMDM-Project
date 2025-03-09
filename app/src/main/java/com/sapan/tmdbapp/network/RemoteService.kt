package com.sapan.tmdbapp.network

import com.sapan.tmdbapp.models.GenresResponse
import com.sapan.tmdbapp.models.MovieListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET(ApiConstants.GENRE_MOVIE)
    suspend fun getGenre(
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ) : Response<GenresResponse>

    @GET(ApiConstants.POPULAR_MOVIE)
    suspend fun getMovies(
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ) : Response<MovieListData>
}