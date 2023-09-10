package com.pg13.data.interfaces

import com.pg13.domain.entities.Resource
import kotlinx.coroutines.flow.Flow

interface DataFetcher<E> {
    fun getData(page: Int = 1): Flow<Resource<E>>
}