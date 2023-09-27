package com.pg13.domain.usecases

import com.pg13.domain.repositories.FavoriteFilmsRepository

class FavoriteFilmsSizeUseCase(private val repository: FavoriteFilmsRepository) {
    suspend fun invoke(): Int = repository.favoriteFilmsSize()
}