package com.pg13.data.util

import android.util.Log
import com.pg13.domain.entities.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException

internal inline fun <ResponseType, ResultType> networkBoundResource(
    crossinline remoteCall: suspend () -> Response<ResponseType>,
    crossinline mapper: (ResponseType) -> ResultType,
): Flow<Resource<ResultType>> = flow {
    emit(Resource.Loading<ResultType>())

    val result = remoteCall.invoke()
    Log.d("test123", "code: ${result.code()}")
    Log.d("test123", "code: ${result.code() == 200 }")
    when {
//        result.code().toString()
//            .startsWith("2") -> emit(Resource.Success<ResultType>(mapper(result.body()!!)))
        result.code() == 200 -> emit(Resource.Success<ResultType>(mapper(result.body()!!)))
        result.code() == 404 -> emit(Resource.Error<ResultType>(message = result.message()))
        result.code() == 401 -> emit(Resource.Error<ResultType>(message = "Ошибка авторизации"))
        result.code().toString()
            .startsWith("5") -> emit(Resource.Error<ResultType>(message = "Внутренняя ошибка сервера"))

        else -> emit(Resource.Error<ResultType>(exception = HttpException(result)))
    }
}.handleException()

internal inline fun <ResultType> networkBoundResource(
    crossinline remoteCall: suspend () -> Response<ResultType>,
): Flow<Resource<ResultType>> = networkBoundResource(remoteCall) { it }


fun <T> Flow<Resource<T>>.handleException()
        : Flow<Resource<T>> = catch { exception ->
    if (exception is IOException) {
        emit(
            Resource.Error<T>(
                exception = exception,
                message = if (exception is ConnectException) "Не удалось подключиться" else "Ошибка доступа в интернет"
            )
        )
    } else {
        emit(
            Resource.Error<T>(
                exception = exception,
                message = "Неизвестная ошибка\n${exception.message}"
            )
        ) // неизвестная ошибка
    }
}