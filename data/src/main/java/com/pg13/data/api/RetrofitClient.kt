package com.pg13.data.api

import com.pg13.data.entities.FilmsRemote
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitClient {
    @GET("api/v2.2/films/top?type=TOP_250_BEST_FILMS&page=1")
    suspend fun getFilmsTop(): Response<FilmsRemote>

}