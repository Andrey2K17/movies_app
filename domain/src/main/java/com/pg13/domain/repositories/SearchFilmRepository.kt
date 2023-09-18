package com.pg13.domain.repositories

import androidx.paging.PagingSource
import com.pg13.domain.entities.Films

interface SearchFilmRepository {
    fun searchFilmByKeyword(query: String): PagingSource<Int, Films.Film>
}