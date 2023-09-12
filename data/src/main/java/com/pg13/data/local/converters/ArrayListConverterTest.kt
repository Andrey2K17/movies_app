package com.pg13.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pg13.data.util.fromJson
import java.lang.Exception

class ArrayListConverterTest<T : Any?> {

    @TypeConverter
    fun fromStringArray(value: ArrayList<T>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): ArrayList<T> {
        return try {
            Gson().fromJson<ArrayList<T>>(value)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}