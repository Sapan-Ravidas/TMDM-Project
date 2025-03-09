package com.sapan.tmdbapp.models.remote

import com.google.gson.annotations.SerializedName

data class MovieListData(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieData>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)