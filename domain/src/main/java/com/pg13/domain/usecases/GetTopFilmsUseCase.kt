package com.pg13.domain.usecases

import androidx.paging.PagingData
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.OrderType
import com.pg13.domain.repositories.FilmsTopRepository
import kotlinx.coroutines.flow.Flow

class GetTopFilmsUseCase(private val repository: FilmsTopRepository) {

    operator fun invoke(order: OrderType): Flow<PagingData<Films.Film>> = repository.getFilmsTop(order)
}