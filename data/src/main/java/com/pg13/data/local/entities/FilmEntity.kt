package com.pg13.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val filmId: Int,
    @ColumnInfo(name = "countries") var countries: List<String>,
    @ColumnInfo(name = "film_length")val filmLength: String,
    @ColumnInfo(name = "genres") val genres: List<String>,
    @ColumnInfo(name = "is_afisha") val isAfisha: Int,
    @ColumnInfo(name = "name_en") val nameEn: String?,
    @ColumnInfo(name = "name_ru") val nameRu: String,
    @ColumnInfo(name = "poster_url") val posterUrl: String,
    @ColumnInfo(name = "poster_url_preview") val posterUrlPreview: String,
    @ColumnInfo(name = "rating") val rating: String,
    @ColumnInfo(name = "rating_voice_count") val ratingVoteCount: Int,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long
)
