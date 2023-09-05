package com.pg13.moviesapp.di

import com.pg13.data.api.RetrofitClient
import com.pg13.data.interfaces.CloudDataSource
import com.pg13.data.net.films.CloudDataSourceGetTopFilms
import com.pg13.data.repositories.FilmTopRepositoryImpl
import com.pg13.domain.entities.Films
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
    fun provideGetTopFilmsUseCase(repository: FilmsTopRepository) : GetTopFilmsUseCase {
        return GetTopFilmsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideFilmTopRepository(cloudDataSource: CloudDataSource<Films>): FilmsTopRepository {
        return FilmTopRepositoryImpl(cloudDataSource)
    }

    @Singleton
    @Provides
    fun provideCloudDataSourceCategories(client: RetrofitClient): CloudDataSource<Films> {
        return CloudDataSourceGetTopFilms(client)
    }
}