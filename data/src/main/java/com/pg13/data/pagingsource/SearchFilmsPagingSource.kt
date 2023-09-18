package com.pg13.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pg13.data.mappers.mapToDomain
import com.pg13.data.remote.service.ApiService
import com.pg13.domain.entities.Films
import retrofit2.HttpException

class SearchFilmsPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, Films.Film>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Films.Film> {
        if (query.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val response = apiService.searchFilmByKeyword(query, pageNumber)

            if (response.isSuccessful) {
                val films = response.body()!!.films.map { it.mapToDomain() }

                val nextPageNumber = if (films.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                return LoadResult.Page(films, prevPageNumber, nextPageNumber)
            } else {
                return LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Films.Film>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    interface Factory {

        fun create(query: String): SearchFilmsPagingSource
    }

    companion object {

        const val INITIAL_PAGE_NUMBER = 1
    }
}