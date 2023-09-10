package com.pg13.data.entities.remote



data class FilmsRemote(
    val films: List<FilmRemote>,
    val pagesCount: Int
) {
    data class FilmRemote(
        val countries: List<CountryRemote>,
        val filmId: Int,
        val filmLength: String,
        val genres: List<GenreRemote>,
        val isAfisha: Int,
        val isRatingUp: Any?,
        val nameEn: String?,
        val nameRu: String,
        val posterUrl: String,
        val posterUrlPreview: String,
        val rating: String,
        val ratingChange: Any?,
        val ratingVoteCount: Int,
        val year: String
    ) {
        data class CountryRemote(
            val country: String
        )

        data class GenreRemote(
            val genre: String
        )
    }
}