package com.pg13.domain.repositories

import com.pg13.domain.entities.Films
import com.pg13.domain.entities.Resource
import kotlinx.coroutines.flow.Flow

interface FilmsTopRepository {
    fun getFilmsTop(page: Int): Flow<Resource<Films>>
}