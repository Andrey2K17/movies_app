package com.pg13.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pg13.data.local.dao.FavoriteFilmDao
import com.pg13.data.mappers.mapToDomain
import com.pg13.domain.entities.Films

class FavoriteFilmPagingSource(
    private val dao: FavoriteFilmDao
) : PagingSource<Int, Films.Film>() {
    override fun getRefreshKey(state: PagingState<Int, Films.Film>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Films.Film> {
        val page = params.key ?: 0

        return try {
            val films = dao.getPagedFilms(params.loadSize, page * params.loadSize).map { it.mapToDomain() }

            LoadResult.Page(
                data = films,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (films.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}