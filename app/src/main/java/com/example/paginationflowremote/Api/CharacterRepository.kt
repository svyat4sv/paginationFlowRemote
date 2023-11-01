package com.example.paginationflowremote.Api

import com.example.paginationflowremote.Api.Database.CharacterDao
import com.example.paginationflowremote.Api.Database.CharacterRemoteKeys
import com.example.paginationflowremote.Api.Database.CharacterRemoteKeysDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CharacterRepository (
    private val apiService: ApiService,
    private val characterDao: CharacterDao,
    private val remoteKeysDao: CharacterRemoteKeysDao
){

    suspend fun saveAllCharacters(characters: List<ResultRickAndMorty>){
       return characterDao.saveAllCharacters(characters)
    }
    suspend fun getAllCharactersDB(): List<ResultRickAndMorty>{
        return characterDao.getAllCharacters()
    }

    fun insertAllCharacterKeys(characterRemoteKeys: List<CharacterRemoteKeys>){
        return remoteKeysDao.insertAllCharacterKeys(characterRemoteKeys)
    }

    fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?>{
       return remoteKeysDao.getNextPageKey(id)
    }

    fun getNextPageKeySimple(id: Int): CharacterRemoteKeys?{
      return  remoteKeysDao.getNextPageKeySimple(id)
    }

    fun deleteAllCharactersKey(){
        return remoteKeysDao.deleteAllCharactersKey()
    }

    suspend fun deleteAllCharacters(characters: List<ResultRickAndMorty>){
        return characterDao.deleteAllCharacters(characters)
    }

    suspend fun getPagesOfCharacters(page: Int): Flow<ApiResponse>{
        return flow {
            emit(apiService.getPagesOfCharacters(page))
        }
    }

}