package com.pg13.domain.entities

data class Films(
    val items: List<Film>,
) {
    data class Film(
        val id: Int,
        val filmId: Int,
        val name: String?,
        val rating: Double?,
        val posterUrlPreview: String?,
        val favorite: Boolean = false
    )
}