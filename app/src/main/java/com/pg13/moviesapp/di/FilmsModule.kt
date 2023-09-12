package com.pg13.moviesapp.di

import com.pg13.data.remote.service.ApiService
import com.pg13.data.local.Database
import com.pg13.data.mediator.FilmMediator
import com.pg13.data.local.dao.FilmsDao
import com.pg13.data.repositories.FilmTopRepositoryImpl
import com.pg13.domain.repositories.FilmsTopRepository
import com.pg13.domain.usecases.GetTopFilmsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FilmsModule {

    @Singleton
    @Provides
    fun provideQueryDao(database: Database): FilmsDao {
        return database.filmDao()
    }

    @Singleton
    @Provides
    fun provideGetTopFilmsUseCase(repository: FilmsTopRepository) : GetTopFilmsUseCase {
        return GetTopFilmsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideFilmTopRepository(service: ApiService, database: Database): FilmsTopRepository {
        return FilmTopRepositoryImpl(service, database)
    }

    @Singleton
    @Provides
    fun provideFilmRemoteMediator(db: Database, api: ApiService): FilmMediator {
        return FilmMediator(db, api)
    }
}