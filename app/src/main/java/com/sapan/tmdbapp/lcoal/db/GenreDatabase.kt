package com.sapan.tmdbapp.lcoal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sapan.tmdbapp.lcoal.dao.GenreDao
import com.sapan.tmdbapp.models.GenreData

@Database(entities = [GenreData::class], version = 1, exportSchema = false)
abstract class GenreDatabase : RoomDatabase() {

    abstract fun genreDao(): GenreDao

    companion object {
        @Volatile
        private var INSTANCE: GenreDatabase? = null

        fun getDatabase(context: Context): GenreDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GenreDatabase::class.java,
                    "genre_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}