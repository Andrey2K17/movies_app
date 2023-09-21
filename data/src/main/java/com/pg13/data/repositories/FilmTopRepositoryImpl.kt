package com.pg13.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.pg13.data.local.Database
import com.pg13.data.mappers.mapToDomain
import com.pg13.data.mediator.FilmMediator
import com.pg13.data.remote.service.ApiService
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.OrderType
import com.pg13.domain.repositories.FilmsTopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilmTopRepositoryImpl(
    private val service: ApiService,
    private val database: Database
): FilmsTopRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getFilmsTop(order: OrderType): Flow<PagingData<Films.Film>> {
        val dbSource = {
            database.filmDao().getPagingFilms()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = FilmMediator(
                database, service, order
            ),
            pagingSourceFactory = dbSource
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.mapToDomain() }
            }
    }
}