package com.pg13.data.mappers

import com.pg13.data.entities.remote.FilmsRemote
import com.pg13.domain.entities.Films

fun FilmsRemote.mapToDomain(): Films = Films(films.map { it.mapToDomain() }, pagesCount);

fun FilmsRemote.FilmRemote.mapToDomain(): Films.Film = Films.Film(
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