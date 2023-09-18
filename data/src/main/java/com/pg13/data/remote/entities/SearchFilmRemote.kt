package com.pg13.data.remote.entities


data class SearchFilmRemote(
    val films: List<FilmRemote>,
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int
) {
    data class FilmRemote(
        val countries: List<CountryRemote>,
        val description: String?,
        val filmId: Int,
        val filmLength: String?,
        val genres: List<GenreRemote>,
        val nameEn: String?,
        val nameRu: String?,
        val posterUrl: String?,
        val posterUrlPreview: String?,
        val rating: String?,
        val ratingVoteCount: Int?,
        val type: String?,
        val year: String?
    )
}