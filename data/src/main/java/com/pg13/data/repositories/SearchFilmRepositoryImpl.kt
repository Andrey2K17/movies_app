package com.pg13.data.repositories

import androidx.paging.PagingSource
import com.pg13.data.pagingsource.SearchFilmsPagingSource
import com.pg13.domain.entities.Films
import com.pg13.domain.repositories.SearchFilmRepository

class SearchFilmRepositoryImpl(
    private val searchFilmsPagingSource: SearchFilmsPagingSource.Factory
): SearchFilmRepository {
    override fun searchFilmByKeyword(query: String): PagingSource<Int, Films.Film> {
        return searchFilmsPagingSource.create(query)
    }

}