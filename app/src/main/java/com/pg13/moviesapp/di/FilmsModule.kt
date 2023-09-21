package com.pg13.moviesapp.di

import com.pg13.data.local.Database
import com.pg13.data.local.dao.FilmsDao
import com.pg13.data.mediator.FilmMediator
import com.pg13.data.pagingsource.SearchFilmsPagingSource
import com.pg13.data.remote.service.ApiService
import com.pg13.data.repositories.FilmTopRepositoryImpl
import com.pg13.data.repositories.SearchFilmRepositoryImpl
import com.pg13.domain.repositories.FilmsTopRepository
import com.pg13.domain.repositories.SearchFilmRepository
import com.pg13.domain.usecases.GetTopFilmsUseCase
import com.pg13.domain.usecases.SearchFilmsUseCase
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
    fun provideGetTopFilmsUseCase(repository: FilmsTopRepository): GetTopFilmsUseCase {
        return GetTopFilmsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideFilmTopRepository(service: ApiService, database: Database): FilmsTopRepository {
        return FilmTopRepositoryImpl(service, database)
    }

//    @Singleton
//    @Provides
//    fun provideFilmRemoteMediator(db: Database, api: ApiService): FilmMediator {
//        return FilmMediator(db, api)
//    }

    @Singleton
    @Provides
    fun provideSearchFilmsUseCase(repository: SearchFilmRepository): SearchFilmsUseCase {
        return SearchFilmsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSearchFilmsRepository(searchFilmsPagingSource: SearchFilmsPagingSource.Factory): SearchFilmRepository {
        return SearchFilmRepositoryImpl(searchFilmsPagingSource)
    }


    @Provides
    fun provideSearchFilmPagingSource(
        api: ApiService,
    ): SearchFilmsPagingSource.Factory {
        return object : SearchFilmsPagingSource.Factory {
            override fun create(query: String): SearchFilmsPagingSource {
                return SearchFilmsPagingSource(api, query)
            }
        }
    }
}