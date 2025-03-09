package com.sapan.tmdbapp.di

import android.content.Context
import com.sapan.tmdbapp.lcoal.dao.GenreDao
import com.sapan.tmdbapp.lcoal.db.GenreDatabase
import com.sapan.tmdbapp.network.RemoteService
import com.sapan.tmdbapp.respository.GenreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGenreDatabase(context: Context): GenreDatabase {
        return GenreDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideGenreDao(database: GenreDatabase): GenreDao {
        return database.genreDao()
    }

    @Provides
    @Singleton
    fun provideGenreRepository(genreDao: GenreDao, remoteService: RemoteService): GenreRepository {
        return GenreRepository(genreDao, remoteService)
    }
}