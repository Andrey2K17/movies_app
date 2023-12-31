package com.pg13.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.pg13.data.local.Database
import com.pg13.data.local.entities.FilmEntity
import com.pg13.data.local.entities.RemoteKey
import com.pg13.data.mappers.mapToDomain
import com.pg13.data.mappers.mapToFavoriteFilmLocal
import com.pg13.data.remote.service.ApiService
import com.pg13.data.util.networkBoundResource
import com.pg13.domain.entities.OrderType
import com.pg13.domain.entities.Resource
import kotlinx.coroutines.flow.toList
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class FilmMediator(
    private val database: Database,
    private val service: ApiService,
    private val order: OrderType
) : RemoteMediator<Int, FilmEntity>() {

    private var pageIndex = 1

    private val remoteKeyDao = database.remoteKeyDao()
    private val filmsDao = database.filmDao()
    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            remoteKeyDao.remoteKeyByQuery("top_films")
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (order == OrderType.EMPTY && (System.currentTimeMillis() - remoteKey.last_updated) <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmEntity>
    ): MediatorResult {

        return try {
            pageIndex = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByQuery("top_films")
                    } ?: return MediatorResult.Success(true)

                    if (remoteKey.next_page == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.next_page

                }
            }

            val filmsFlow = networkBoundResource(
                {
                    if (order.type.isEmpty()) service.getFilmsByOrder(page = pageIndex) else service.getFilmsByOrder(
                        page = pageIndex,
                        order = order.type
                    )
                },
                { it -> it.items.map { it.mapToFavoriteFilmLocal() } }
            )

            when (val filmsData = filmsFlow.toList().last()) {
                is Resource.Error -> MediatorResult.Error(filmsData.exception!!)
                is Resource.Success -> {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            filmsDao.clearFilms()
                        }

                        val nextPage = if (filmsData.data.isEmpty()) {
                            null
                        } else {
                            pageIndex + 1
                        }

                        remoteKeyDao.insertOrReplace(
                            RemoteKey(
                                id = "top_films",
                                next_page = nextPage,
                                last_updated = System.currentTimeMillis()
                            )
                        )

                        val favoriteFilms = database.favoriteFilmDao().getFilms().map { it.mapToDomain() }
                        val remoteFilms = filmsData.data
                        val films = remoteFilms.onEach {
                            it.favorite = favoriteFilms.any { favoriteFilms -> favoriteFilms.filmId == it.filmId }
                        }

                        filmsDao.saveFilms(films)
                    }

                    MediatorResult.Success(
                        endOfPaginationReached = filmsData.data.isEmpty()
                    )
                }

                is Resource.Loading -> TODO()
            }
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}