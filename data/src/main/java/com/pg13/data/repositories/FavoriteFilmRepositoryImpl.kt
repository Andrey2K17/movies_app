package com.pg13.data.repositories

import androidx.paging.PagingSource
import com.pg13.data.local.Database
import com.pg13.data.mappers.mapToFavoriteFilmLocal
import com.pg13.data.pagingsource.FavoriteFilmPagingSource
import com.pg13.domain.entities.Films
import com.pg13.domain.repositories.FavoriteFilmsRepository

class FavoriteFilmRepositoryImpl(
    private val favoriteFilmPagingSource: FavoriteFilmPagingSource,
    private val database: Database
) : FavoriteFilmsRepository {

    override fun favoriteFilmPagingSource(): PagingSource<Int, Films.Film> {
        return favoriteFilmPagingSource
    }

    override suspend fun addToFavorite(film: Films.Film): Int {
        val filmEntity = film.mapToFavoriteFilmLocal()
        return if (filmEntity.favorite) {
            val saveFilm = database.favoriteFilmDao().saveFilm(filmEntity).toInt()
            if (saveFilm != 0) {
                database.filmDao().updateFilm(true, film.filmId)
            }
            saveFilm
        } else {
            val removeFilm = database.favoriteFilmDao().removeFilm(filmEntity)
            if (removeFilm != 0) database.filmDao().updateFilm(false, film.filmId)
            removeFilm
        }

    }

    override suspend fun favoriteFilmsSize(): Int {
        return database.favoriteFilmDao().getSize()
    }
}