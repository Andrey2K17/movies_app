package com.pg13.data.mappers

import com.pg13.data.local.entities.FilmEntity
import com.pg13.data.remote.entities.FilmsRemote
import com.pg13.data.remote.entities.SearchFilmRemote
import com.pg13.domain.entities.Films

fun FilmsRemote.FilmRemote.mapToLocal(): FilmEntity = FilmEntity(
    filmId = filmId,
    name = nameRu ?: nameOriginal ?: "empty name",
    rating = rating ?: 0.0,
    posterUrlPreview = posterUrlPreview ?: ""
)

fun FilmEntity.mapToDomain(): Films.Film = Films.Film(
    id = id ?: 0,
    filmId = filmId,
    name = name,
    rating = rating,
    posterUrlPreview = posterUrlPreview
)

fun SearchFilmRemote.FilmRemote.mapToDomain(): Films.Film = Films.Film(
    id = 0,
    filmId = filmId,
    name = name,
    rating = if (rating == "null") 0.0 else rating.toDouble(),
    posterUrlPreview = posterUrlPreview
)
