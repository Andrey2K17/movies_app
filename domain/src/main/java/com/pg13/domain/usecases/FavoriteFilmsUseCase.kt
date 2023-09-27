package com.pg13.domain.usecases

import androidx.paging.PagingSource
import com.pg13.domain.entities.Films
import com.pg13.domain.repositories.FavoriteFilmsRepository

class FavoriteFilmsUseCase(private val repository: FavoriteFilmsRepository) {
    operator fun invoke(): PagingSource<Int, Films.Film> = repository.favoriteFilmPagingSource()
}