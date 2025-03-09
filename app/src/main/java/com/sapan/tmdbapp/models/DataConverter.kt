package com.sapan.tmdbapp.models

import com.sapan.tmdbapp.models.local.Bookmark
import com.sapan.tmdbapp.models.local.Movie

interface DataConverter<Domain, Entity> {
    fun mapToDomain(apiData: Entity, category: String) : Domain
    fun mapToBookmark(movie: Movie): Bookmark
}