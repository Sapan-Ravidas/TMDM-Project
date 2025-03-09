package com.sapan.tmdbapp.lcoal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sapan.tmdbapp.lcoal.dao.MovieDao
import com.sapan.tmdbapp.models.GenreData
import com.sapan.tmdbapp.models.local.Bookmark
import com.sapan.tmdbapp.models.local.Movie

@Database(entities = [
        GenreData::class,
        Movie::class,
        Bookmark::class],
    version = 1,
    exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "genre_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}