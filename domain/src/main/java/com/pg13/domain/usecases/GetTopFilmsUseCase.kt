package com.pg13.domain.usecases

import com.pg13.domain.entities.Films
import com.pg13.domain.entities.Resource
import com.pg13.domain.repositories.FilmsTopRepository
import kotlinx.coroutines.flow.Flow

class GetTopFilmsUseCase(private val repository: FilmsTopRepository) {

    operator fun invoke(page: Int): Flow<Resource<Films>> = repository.getFilmsTop(page)
}