package com.example.paginationflowremote.Api.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class CharacterRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "character_id")
    val characterID: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)