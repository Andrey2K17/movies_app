package com.pg13.data.remote.entities



data class FilmsRemote(
    val films: List<FilmRemote>,
    val pagesCount: Int
) {
    data class FilmRemote(
        val countries: List<CountryRemote>,
        val filmId: Int,
        val filmLength: String?,
        val genres: List<GenreRemote>,
        val isAfisha: Int?,
        val isRatingUp: Any?,
        val nameEn: String?,
        val nameRu: String,
        val posterUrl: String?,
        val posterUrlPreview: String?,
        val rating: String?,
        val ratingChange: Any?,
        val ratingVoteCount: Int?,
        val year: String?
    )
}