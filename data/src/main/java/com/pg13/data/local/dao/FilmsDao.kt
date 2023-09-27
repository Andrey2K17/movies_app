package com.pg13.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.pg13.data.local.entities.FilmEntity

@Dao
interface FilmsDao {
    @Upsert
    suspend fun saveFilms(list: List<FilmEntity>)

    @Query("UPDATE films SET favorite = :favorite WHERE film_id = :filmId")
    suspend fun updateFilm(favorite: Boolean, filmId: Int): Int

    @Query("SELECT * FROM films")
    fun getPagingFilms(): PagingSource<Int, FilmEntity>

    @Query("DELETE FROM films")
    suspend fun clearFilms()
}