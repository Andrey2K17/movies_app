package com.pg13.data.remote.service

import androidx.annotation.IntRange
import com.pg13.data.remote.entities.FilmsRemote
import com.pg13.data.remote.entities.SearchFilmRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v2.2/films/top")
    suspend fun getFilmsTop(
        @Query("type") type: String = "TOP_250_BEST_FILMS",
        @Query("page") @IntRange(from = 1) page: Int = 1
    ): Response<FilmsRemote>

    @GET("api/v2.2/films")
    suspend fun getFilmsByOrder(
        @Query("type") type: String = "ALL",
        @Query("ratingFrom") ratingFrom: Int = 0,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 1000,
        @Query("yearTo") yearTo: Int = 3000,
        @Query("page") @IntRange(from = 1) page: Int = 1,
    ): Response<FilmsRemote>

    @GET("api/v2.2/films")
    suspend fun getFilmsByOrder(
        @Query("order") order: String = "",
        @Query("type") type: String = "ALL",
        @Query("ratingFrom") ratingFrom: Int = 0,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 1000,
        @Query("yearTo") yearTo: Int = 3000,
        @Query("page") @IntRange(from = 1) page: Int = 1,
    ): Response<FilmsRemote>

    @GET("api/v2.1/films/search-by-keyword")
    suspend fun searchFilmByKeyword(
        @Query("keyword") keyword: String,
        @Query("page") @IntRange(from = 1) page: Int = 1
    ): Response<SearchFilmRemote>

    companion object {

        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 20
    }
}

