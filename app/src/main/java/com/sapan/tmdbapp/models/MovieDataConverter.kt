package com.sapan.tmdbapp.models

import com.sapan.tmdbapp.models.local.Movie
import com.sapan.tmdbapp.models.remote.MovieListData


class MovieDataConverter : DataConverter<List<Movie>, MovieListData> {
    override fun mapToDomain(apiData: MovieListData, category: String): List<Movie> {
        return apiData.results?.map { result ->
            Movie(
                backdropPath = formatEmptyValue(result?.backdropPath),
                genreIds = formatGenre(result?.genreIds),
                id = result?.id ?: 0,
                originalLanguage = formatEmptyValue(result?.originalLanguage, "language"),
                originalTitle = formatEmptyValue(result?.originalTitle, "title"),
                overview = formatEmptyValue(result?.overview, "overview"),
                popularity = result?.popularity ?: 0.0,
                posterPath = formatEmptyValue(result?.posterPath),
                releaseDate = formatEmptyValue(result?.releaseDate, "date"),
                title = formatEmptyValue(result?.title, "title"),
                voteAverage = result?.voteAverage ?: 0.0,
                voteCount = result?.voteCount ?: 0,
                video = result?.video ?: false,
                category = category
            )
        } ?: emptyList()
    }

    private fun formatEmptyValue(value: String?, default: String = ""): String {
        if (value.isNullOrEmpty()) return "Unknown $default"
        return value
    }

    private fun formatGenre(genreIds: List<Int>?): String {
        return genreIds?.joinToString(",") { it.toString() } ?: ""
    }

}