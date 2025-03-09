package com.sapan.tmdbapp.lcoal.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sapan.tmdbapp.models.GenreData
import com.sapan.tmdbapp.models.local.Bookmark
import com.sapan.tmdbapp.models.local.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsetGenres(genres: List<GenreData>)

    @Query("SELECT * FROM genre_table")
    fun getAllGenres(): Flow<List<GenreData>>

    @Query("DELETE FROM genre_table")
    suspend fun deleteAllGenres()

    /**
     *
     */
    @Upsert
    suspend fun upsertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies WHERE category = :category")
    fun getMovies(category: String): Flow<List<Movie>>

    @Query("DELETE FROM movies WHERE category = :category")
    suspend fun deleteMovies(category: String)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    /**
     *
     */
    @Upsert
    suspend fun insertBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE id = :movieId")
    suspend fun deleteBookmark(movieId: Int)

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<Bookmark>>
}