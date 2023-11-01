package com.example.paginationflowremote.Api.Database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paginationflowremote.Api.ResultRickAndMorty

@Dao
interface CharacterDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun saveAllCharacters(characters: List<ResultRickAndMorty>)

        @Query("SELECT * FROM character")
        suspend fun getAllCharacters(): List<ResultRickAndMorty>

        @Query("SELECT * FROM character")
        fun getPagesOfCharactersDB(): PagingSource<Int, ResultRickAndMorty>

        @Delete
        suspend fun deleteAllCharacters(characters: List<ResultRickAndMorty>)

}