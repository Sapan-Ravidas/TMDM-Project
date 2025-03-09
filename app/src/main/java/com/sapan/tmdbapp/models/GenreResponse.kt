package com.sapan.tmdbapp.models

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres") val genres: List<GenreData>
)