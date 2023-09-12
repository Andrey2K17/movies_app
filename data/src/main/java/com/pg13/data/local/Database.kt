package com.pg13.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pg13.data.db.converters.ListConverter
import com.pg13.data.local.dao.FilmsDao
import com.pg13.data.local.dao.RemoteKeyDao
import com.pg13.data.local.entities.FilmEntity
import com.pg13.data.local.entities.RemoteKey

@Database(entities = [FilmEntity::class, RemoteKey::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class Database: RoomDatabase() {
    abstract fun filmDao(): FilmsDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}