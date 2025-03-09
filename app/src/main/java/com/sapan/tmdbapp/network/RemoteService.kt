package com.sapan.tmdbapp.network

import com.sapan.tmdbapp.models.GenresResponse
import com.sapan.tmdbapp.models.remote.MovieListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

    @GET(ApiConstants.GENRE_MOVIE)
    suspend fun getGenre(
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ) : Response<GenresResponse>

    @GET("${ApiConstants.MOVIE_END_POINT}/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ) : Response<MovieListData>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ): Response<MovieListData>
}