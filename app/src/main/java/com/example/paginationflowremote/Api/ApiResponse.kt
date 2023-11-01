package com.example.paginationflowremote.Api

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.paginationflowremote.Api.TypeAdapters.NameUrlConverter
import com.example.paginationflowremote.Api.TypeAdapters.StringListConverter
import kotlinx.parcelize.Parcelize


data class ApiResponse(
    val info: Info?,
    val results: List<ResultRickAndMorty> = listOf()
)

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any?
)

@Parcelize
@Entity(tableName = "character")
@TypeConverters(StringListConverter::class, NameUrlConverter::class)
data class ResultRickAndMorty(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var created: String = "",
    var episode: List<String> = listOf(),
    var gender: String = "",
    var image: String = "",
    var nameUrl: NameUrl = NameUrl("", ""),
    var name: String = "",
    var location: NameUrl = NameUrl("", ""),
    var species: String = "",
    var status: String = "",
    var type: String = "",
    var url: String = ""
) : Parcelable

data class Location(
    val name: String,
    val url: String,
)

data class Origin(
    val name: String,
    val url: String
)