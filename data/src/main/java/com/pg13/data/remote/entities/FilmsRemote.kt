package com.pg13.data.remote.entities

import com.google.gson.annotations.SerializedName


data class FilmsRemote(
    val items: List<FilmRemote>,
) {
    data class FilmRemote(
        @SerializedName("kinopoiskId") val filmId: Int,
        @SerializedName("nameOriginal") val nameOriginal: String?,
        @SerializedName("nameRu") val nameRu: String?,
        @SerializedName("ratingKinopoisk") val rating: Double?,
        val posterUrlPreview: String?
    )
}