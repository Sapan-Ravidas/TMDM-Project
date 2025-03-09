package com.sapan.tmdbapp.di

import android.content.Context
import com.sapan.tmdbapp.lcoal.dao.GenreDao
import com.sapan.tmdbapp.lcoal.db.GenreDatabase
import com.sapan.tmdbapp.network.RemoteService
import com.sapan.tmdbapp.respository.GenreRepository
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
    fun provideGenreDatabase(@ApplicationContext context: Context): GenreDatabase {
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