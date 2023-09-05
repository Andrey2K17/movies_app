package com.pg13.data.interfaces

import com.pg13.domain.entities.Resource
import kotlinx.coroutines.flow.Flow

interface DataFetcher<E> {
    fun getData(): Flow<Resource<E>>
}