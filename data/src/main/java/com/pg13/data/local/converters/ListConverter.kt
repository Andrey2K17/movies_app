package com.pg13.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pg13.data.util.fromJson
import java.lang.Exception

class ListConverter {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(value)
        } catch (e: Exception) {
            listOf()
        }
    }
}