package com.pg13.data.net.films

import com.pg13.data.api.RetrofitClient
import com.pg13.data.interfaces.CloudDataSource
import com.pg13.data.mappers.mapToDomain
import com.pg13.data.util.networkBoundResource
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.Resource
import kotlinx.coroutines.flow.Flow

class CloudDataSourceGetTopFilms(
    private val api: RetrofitClient
) : CloudDataSource<Films> {
    override fun getData(): Flow<Resource<Films>> = networkBoundResource(
        { api.getFilmsTop() },
        { cloudData -> cloudData.mapToDomain() }
    )
}