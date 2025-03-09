package com.sapan.tmdbapp.network

object ApiConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
    const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
    const val YOUTUBE_THUMBNAIL_URL_ENDPOINT = "/sddefault.jpg"

    const val URL_REPOSITORIES = "photos"
    const val DISCOVER_MOVIE = "discover/movie"
    const val UPCOMING_MOVIE = "movie/upcoming"
    const val POPULAR_MOVIE = "movie/popular"
    const val TOP_RATED_MOVIE = "movie/top_rated"
    const val GENRE_MOVIE = "genre/movie/list"
    const val MOVIE_DETAILS = "movie/{movieId}"
    const val TRAILER_VIDEO = "movie/{movieId}/videos"

    const val API_KEY = "957b58b1eecccb544ecfe546b6129ca2"
}