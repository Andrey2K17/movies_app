package com.pg13.data.repositories

import com.pg13.data.interfaces.CloudDataSource
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.Resource
import com.pg13.domain.repositories.FilmsTopRepository
import kotlinx.coroutines.flow.Flow

class FilmTopRepositoryImpl(
    private val cloudDataSource: CloudDataSource<Films>
): FilmsTopRepository {
    override fun getFilmsTop(): Flow<Resource<Films>> = cloudDataSource.getData()
}