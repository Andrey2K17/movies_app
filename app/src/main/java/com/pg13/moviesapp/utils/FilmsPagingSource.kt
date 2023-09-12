package com.pg13.moviesapp.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.Resource
import com.pg13.domain.usecases.GetTopFilmsUseCase
import kotlinx.coroutines.flow.toList
import retrofit2.HttpException

class FilmsPagingSource(
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
) : PagingSource<Int, Films.Film>() {
    override fun getRefreshKey(state: PagingState<Int, Films.Film>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Films.Film> {
        try {
            return LoadResult.Error(Exception())
//            val page: Int = params.key ?: 1
//            val pageSize: Int = params.loadSize
//
//            val filmsFlow = getTopFilmsUseCase.invoke()
//            val filmsData: Resource<Films> = filmsFlow.toList().last()
//
//            if (filmsData is Resource.Error) {
//                return LoadResult.Error(Exception(filmsData.message ?: "Произошла ошибка"))
//            } else {
//                val films = checkNotNull(filmsData.data)
//                val nextKey = if (films.films.size < pageSize) null else page + 1
//                val prevKey = if (page == 1) null else page - 1
//                return LoadResult.Page(films.films, prevKey, nextKey)
//            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}