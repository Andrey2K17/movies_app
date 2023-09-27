package com.pg13.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_films")
data class FavoriteFilmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "film_id") val filmId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "poster_url_preview") val posterUrlPreview: String,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "favorite") val favorite: Boolean = false
)