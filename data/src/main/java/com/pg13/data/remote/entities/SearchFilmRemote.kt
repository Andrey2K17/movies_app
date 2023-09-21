package com.pg13.data.remote.entities

import com.google.gson.annotations.SerializedName


data class SearchFilmRemote(
    val films: List<FilmRemote>,
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int
) {
    data class FilmRemote(
        val filmId: Int,
        @SerializedName("nameRu") val name: String?,
        val rating: String,
        val posterUrlPreview: String?,
    )
}