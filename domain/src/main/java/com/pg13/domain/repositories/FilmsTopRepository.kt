package com.pg13.domain.repositories

import androidx.paging.PagingData
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.OrderType
import kotlinx.coroutines.flow.Flow

interface FilmsTopRepository {
    fun getFilmsTop(order: OrderType): Flow<PagingData<Films.Film>>
}