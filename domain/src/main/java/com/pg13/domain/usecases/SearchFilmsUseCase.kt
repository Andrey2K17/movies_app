package com.pg13.domain.usecases

import androidx.paging.PagingSource
import com.pg13.domain.entities.Films
import com.pg13.domain.repositories.SearchFilmRepository

class SearchFilmsUseCase(private val repository: SearchFilmRepository) {
    operator fun invoke(query: String): PagingSource<Int, Films.Film>
        = repository.searchFilmByKeyword(query)
}