package com.example.paginationflowremote

import android.widget.Toast
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paginationflowremote.Api.ApiResponse
import com.example.paginationflowremote.Api.CharacterRepository
import com.example.paginationflowremote.Api.Database.AppDatabase
import com.example.paginationflowremote.Api.ResultRickAndMorty
import kotlinx.coroutines.flow.Flow


class CharacterPagingSource(private val repository: CharacterRepository) :
    PagingSource<Int, ResultRickAndMorty>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultRickAndMorty> {
        val currentLoadingPageKey = params.key ?: 1

        return try {
            var characters = ApiResponse(null)
            repository.getPagesOfCharacters(currentLoadingPageKey).collect {
                characters = it
                repository.saveAllCharacters(it.results)
            }

            val data = characters.results

//            val data = repository.getAllCharactersDB()

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultRickAndMorty>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

}

