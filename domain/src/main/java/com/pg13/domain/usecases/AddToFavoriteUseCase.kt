package com.pg13.domain.usecases

import com.pg13.domain.entities.Films
import com.pg13.domain.repositories.FavoriteFilmsRepository

class AddToFavoriteUseCase(private val repository: FavoriteFilmsRepository) {
    suspend operator fun invoke(film: Films.Film): Int = repository.addToFavorite(film)
}