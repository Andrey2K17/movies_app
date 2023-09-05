package com.pg13.data.interfaces

interface CacheDataSource<E>: DataFetcher<E> {
    suspend fun getDataList(): List<E>

    suspend fun add(item: E)
}