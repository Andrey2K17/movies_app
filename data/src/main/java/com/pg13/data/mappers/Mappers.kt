package com.pg13.data.mappers

import com.pg13.data.local.entities.FilmEntity
import com.pg13.data.remote.entities.CountryRemote
import com.pg13.data.remote.entities.FilmsRemote
import com.pg13.data.remote.entities.GenreRemote
import com.pg13.data.remote.entities.SearchFilmRemote
import com.pg13.domain.entities.Country
import com.pg13.domain.entities.Films
import com.pg13.domain.entities.Genre

fun FilmsRemote.mapToDomain(): Films = Films(films.map { it.mapToDomain() }, pagesCount);

fun FilmsRemote.FilmRemote.mapToDomain(): Films.Film = Films.Film(
    0,
    countries.map { it.mapToDomain() },
    filmId,
    filmLength ?: "",
    genres.map { it.mapToDomain() },
    isAfisha ?: 0,
    isRatingUp,
    nameEn,
    nameRu,
    posterUrl ?: "",
    posterUrlPreview ?: "",
    rating ?: "",
    ratingChange,
    ratingVoteCount ?: 0,
    year ?: ""
)

fun CountryRemote.mapToDomain(): Country = Country(country)

fun GenreRemote.mapToDomain(): Genre = Genre(genre)

fun FilmsRemote.FilmRemote.mapToLocal(): FilmEntity = FilmEntity(
    id = 0,
    filmId,
    countries.map { it.country },
    filmLength ?: "",
    genres.map { it.genre },
    isAfisha ?: 0,
    nameEn,
    nameRu,
    posterUrl ?: "",
    posterUrlPreview ?: "",
    rating ?: "",
    ratingVoteCount ?: 0,
    year ?: "",
    System.currentTimeMillis()
)

fun FilmEntity.mapToDomain(): Films.Film = Films.Film(
    id,
    countries.map { Country(it) },
    filmId,
    filmLength,
    genres.map { Genre(it) },
    isAfisha,
    null,
    nameEn,
    nameRu,
    posterUrl,
    posterUrlPreview,
    rating,
    null,
    ratingVoteCount,
    year
)

fun SearchFilmRemote.FilmRemote.mapToDomain(): Films.Film = Films.Film(
    0,
    countries.map { it.mapToDomain() },
    //description ?: "",
    filmId,
    filmLength ?: "",
    genres.map { it.mapToDomain() },
    isAfisha = 0,
    isRatingUp = null,
    nameEn ?: "",
    nameRu ?: "",
    posterUrl ?: "",
    posterUrlPreview ?: "",
    rating ?: "",
    ratingChange = null,
    ratingVoteCount ?: 0,
    year ?: ""
)