package com.example.paginationflowremote.Api.TypeAdapters

import androidx.room.TypeConverter
import com.example.paginationflowremote.Api.NameUrl
import com.google.gson.Gson

class NameUrlConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromNameUrl(nameUrl: NameUrl): String {
        return gson.toJson(nameUrl)
    }

    @TypeConverter
    fun toNameUrl(json: String): NameUrl {
        return gson.fromJson(json, NameUrl::class.java)
    }
}

class StringListConverter {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}