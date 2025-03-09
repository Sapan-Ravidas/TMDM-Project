package com.sapan.tmdbapp.network

object ApiConstants {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val API_KEY = ""
    const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
    const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
    const val YOUTUBE_THUMBNAIL_URL_ENDPOINT = "/sddefault.jpg"

    const val URL_REPOSITORIES = "photos"
    const val DISCOVER_MOVIE = "3/discover/movie"
    const val UPCOMING_MOVIE = "3/movie/upcoming"
    const val POPULAR_MOVIE = "3/movie/popular"
    const val TOP_RATED_MOVIE = "3/movie/top_rated"
    const val GENRE_MOVIE = "3/genre/movie/list"
    const val MOVIE_DETAILS = "3/movie/{movieId}"
    const val TRAILER_VIDEO = "3/movie/{movieId}/videos"
}