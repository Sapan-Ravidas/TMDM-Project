package com.sapan.tmdbapp.di

import android.content.Context
import com.sapan.tmdbapp.lcoal.dao.MovieDao
import com.sapan.tmdbapp.lcoal.db.MovieDatabase
import com.sapan.tmdbapp.network.RemoteService
import com.sapan.tmdbapp.respository.MovieRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val moshi  = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()!!

    @Provides
    @Singleton
    fun provideGenreDatabase(@ApplicationContext context: Context): MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideGenreDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideGenreRepository(
        movieDao: MovieDao,
        remoteService: RemoteService
    ): MovieRepository {
        return MovieRepository(movieDao, remoteService)
    }


}