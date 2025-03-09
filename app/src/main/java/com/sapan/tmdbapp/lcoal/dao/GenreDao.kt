package com.sapan.tmdbapp.lcoal.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sapan.tmdbapp.models.GenreData
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Upsert
    suspend fun upsetGenres(genres: List<GenreData>)

    @Query("SELECT * FROM genre_table")
    fun getAllGenres(): Flow<List<GenreData>>

    @Query("DELETE FROM genre_table")
    suspend fun deleteAllGenres()
}