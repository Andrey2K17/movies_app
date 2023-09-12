package com.pg13.data.remote.service

import androidx.annotation.IntRange
import com.pg13.data.remote.entities.FilmsRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v2.2/films/top")
    suspend fun getFilmsTop(
        @Query("type") type: String = "TOP_250_BEST_FILMS",
        @Query("page") @IntRange(from = 1) page: Int = 1
    ): Response<FilmsRemote>

}