package com.example.paginationflowremote.Api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.paginationflowremote.Api.Database.AppDatabase
import com.example.paginationflowremote.Api.Database.CharacterDao
import com.example.paginationflowremote.Api.Database.CharacterRemoteKeys
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val repository: CharacterRepository
): RemoteMediator<Int, ResultRickAndMorty>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultRickAndMorty>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            var characters = ApiResponse(null)

            repository.getPagesOfCharacters(page).collect {
                characters = it
            }


            val listOfRick = characters.results

            val endOfPaginationReached = listOfRick.isEmpty()
            if (loadType == LoadType.REFRESH) {
                repository.deleteAllCharactersKey()
                repository.deleteAllCharacters(listOfRick)

            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1

            println("!!!!  prevKey $prevKey nextKey $nextKey")


            val keys = listOfRick.map {
                CharacterRemoteKeys(characterID = it.id, prevKey = prevKey, nextKey = nextKey, currentPage = page)
            }

            repository.insertAllCharacterKeys(keys)

            repository.saveAllCharacters(listOfRick)


            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


        private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ResultRickAndMorty>): CharacterRemoteKeys? {
            // Get the last page that was retrieved, that contained items.
            // From that last page, get the last item
            return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { rick ->

                    repository.getNextPageKeySimple(rick.id)
                }
        }

        private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ResultRickAndMorty>): CharacterRemoteKeys? {
            // Get the first page that was retrieved, that contained items.
            // From that first page, get the first item
            return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                ?.let { rick ->
                    // Get the remote keys of the first items retrieved
                    repository.getNextPageKeySimple(rick.id)
                }
        }

        private suspend fun getRemoteKeyClosestToCurrentPosition(
            state: PagingState<Int, ResultRickAndMorty>
        ): CharacterRemoteKeys? {
            // The paging library is trying to load data after the anchor position
            // Get the item closest to the anchor position
            return state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { rickId ->
                    repository.getNextPageKeySimple(rickId)
                }
            }
        }
}
