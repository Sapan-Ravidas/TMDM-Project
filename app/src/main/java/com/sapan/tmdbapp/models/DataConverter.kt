package com.sapan.tmdbapp.models

interface DataConverter<Domain, Entity> {
    fun mapToDomain(apiData: Entity, category: String) : Domain
}