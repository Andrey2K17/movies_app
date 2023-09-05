package com.pg13.domain.entities

sealed class Resource<out T>(open val data: T?) {

    class Success<T>(override val data: T) : Resource<T>(data)

    class Loading<T>(data: T? = null) : Resource<T>(data)

    class Error<T>(
        data: T? = null,
        val exception: Throwable? = null,
        val message: String? = null
    ) : Resource<T>(data)
}