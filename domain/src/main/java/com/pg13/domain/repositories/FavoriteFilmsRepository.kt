package com.pg13.domain.repositories

import androidx.paging.PagingSource
import com.pg13.domain.entities.Films

interface FavoriteFilmsRepository {
    fun favoriteFilmPagingSource(): PagingSource<Int, Films.Film>

    suspend fun addToFavorite(film: Films.Film): Int

    suspend fun favoriteFilmsSize(): Int
}