package com.pg13.domain.entities

data class Films(
    val films: List<Film>,
    val pagesCount: Int
) {
    data class Film(
        val id: Int,
        val countries: List<Country>,
        val filmId: Int,
        val filmLength: String,
        val genres: List<Genre>,
        val isAfisha: Int,
        val isRatingUp: Any?,
        val nameEn: String?,
        val nameRu: String,
        val posterUrl: String,
        val posterUrlPreview: String,
        val rating: String,
        val ratingChange: Any?,
        val ratingVoteCount: Int,
        val year: String,
        val favorite: Boolean = false
    )
}