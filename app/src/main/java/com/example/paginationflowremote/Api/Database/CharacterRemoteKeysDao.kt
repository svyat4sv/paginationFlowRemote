package com.example.paginationflowremote.Api.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCharacterKeys(characterRemoteKeys: List<CharacterRemoteKeys>)


    @Query("SELECT * FROM remote_key WHERE character_id LIKE :id")
    fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?>


    @Query("SELECT * FROM remote_key WHERE character_id LIKE :id")
    fun getNextPageKeySimple(id: Int): CharacterRemoteKeys?


    @Query("DELETE FROM remote_key")
    fun deleteAllCharactersKey()
}