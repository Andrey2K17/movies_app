package com.pg13.domain.repositories

import androidx.paging.PagingData
import com.pg13.domain.entities.Films
import kotlinx.coroutines.flow.Flow

interface FilmsTopRepository {
    fun getFilmsTop(): Flow<PagingData<Films.Film>>
}