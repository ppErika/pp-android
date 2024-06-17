package com.pp.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import android.util.Base64

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromByteArrayList(value: List<ByteArray>?): String {
        val type = object : TypeToken<List<String>>() {}.type
        val base64Strings = value?.map { Base64.encodeToString(it, Base64.DEFAULT) } ?: emptyList()
        return Gson().toJson(base64Strings, type)
    }

    @TypeConverter
    fun toByteArrayList(value: String): List<ByteArray> {
        val type = object : TypeToken<List<String>>() {}.type
        val base64Strings: List<String> = Gson().fromJson(value, type)
        return base64Strings.map { Base64.decode(it, Base64.DEFAULT) }
    }
}
