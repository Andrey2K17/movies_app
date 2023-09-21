package com.pg13.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.pg13.data.local.entities.FilmEntity

@Dao
interface FilmsDao {
//    @Query("SELECT * FROM films")
//    suspend fun getAll(): List<FilmsLocal>

    @Upsert
    suspend fun saveFilms(list: List<FilmEntity>)

    @Query("SELECT * FROM films")
    fun getPagingFilms(): PagingSource<Int, FilmEntity>

//    @Query("SELECT * FROM films ORDER BY rating DESC")
//    fun getPagingFilms(): PagingSource<Int, FilmEntity>

    @Query("DELETE FROM films")
    suspend fun clearFilms()
}