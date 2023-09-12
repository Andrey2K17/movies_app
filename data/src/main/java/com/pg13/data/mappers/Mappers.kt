package com.pg13.data.mappers

import com.pg13.data.remote.entities.FilmsRemote
import com.pg13.data.local.entities.FilmEntity
import com.pg13.domain.entities.Films

fun FilmsRemote.mapToDomain(): Films = Films(films.map { it.mapToDomain() }, pagesCount);

fun FilmsRemote.FilmRemote.mapToDomain(): Films.Film = Films.Film(
    0,
    countries.map { it.mapToDomain() },
    filmId,
    filmLength,
    genres.map { it.mapToDomain() },
    isAfisha,
    isRatingUp,
    nameEn,
    nameRu,
    posterUrl,
    posterUrlPreview,
    rating,
    ratingChange,
    ratingVoteCount,
    year
)

fun FilmsRemote.FilmRemote.CountryRemote.mapToDomain(): Films.Film.Country =
    Films.Film.Country(country)

fun FilmsRemote.FilmRemote.GenreRemote.mapToDomain(): Films.Film.Genre = Films.Film.Genre(genre)

fun FilmsRemote.FilmRemote.mapToLocal(): FilmEntity = FilmEntity(
    id = 0,
    filmId,
    countries.map { it.country },
    filmLength,
    genres.map { it.genre },
    isAfisha,
    nameEn,
    nameRu,
    posterUrl,
    posterUrlPreview,
    rating,
    ratingVoteCount,
    year,
    System.currentTimeMillis()
)

fun FilmEntity.mapToDomain(): Films.Film = Films.Film(
    id,
    countries.map { Films.Film.Country(it) },
    filmId,
    filmLength,
    genres.map { Films.Film.Genre(it) },
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