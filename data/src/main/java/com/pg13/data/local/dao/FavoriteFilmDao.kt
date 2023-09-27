package com.pg13.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pg13.data.local.entities.FavoriteFilmEntity

@Dao
interface FavoriteFilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilm(query: FavoriteFilmEntity): Long

    @Query("SELECT * FROM favorite_films ORDER BY id ASC LIMIT :limit OFFSET :offset ")
    suspend fun getPagedFilms(limit: Int, offset: Int): List<FavoriteFilmEntity>

    @Query("SELECT * FROM favorite_films")
    suspend fun getFilms(): List<FavoriteFilmEntity>

    @Delete
    suspend fun removeFilm(post: FavoriteFilmEntity): Int

    @Query("SELECT COUNT(*) FROM favorite_films")
    suspend fun getSize(): Int
}