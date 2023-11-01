package com.example.paginationflowremote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paginationflowremote.Api.CharacterRemoteMediator
import com.example.paginationflowremote.Api.CharacterRepository
import com.example.paginationflowremote.Api.ResultRickAndMorty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CharactersViewModel(private val repo: CharacterRepository)
    : ViewModel() {

    private val pagerConfig = PagingConfig(
        pageSize = 10
    )

    @OptIn(ExperimentalPagingApi::class)
    private val pager = Pager(config = pagerConfig
        , pagingSourceFactory = {
        CharacterPagingSource(repo)
    }
        , remoteMediator = CharacterRemoteMediator(repo))

    val pagingData: Flow<PagingData<ResultRickAndMorty>> = pager.flow.cachedIn(viewModelScope)










//    fun getPagingData(): Flow<PagingData<ResultRickAndMorty>> {
//        return Pager(
//            config = PagingConfig(pageSize = 20),
//            pagingSourceFactory = { CharacterPagingSource(repo) } // Replace with your paging source
//        ).flow.cachedIn(viewModelScope)
//    }
//
//    // Example function to load data with proper background thread handling
//    fun loadData() {
//        viewModelScope.launch {
//            try {
//                // Perform database operation on a background thread
//                val result = withContext(Dispatchers.IO) {
//                    repo.getAllCharactersDB()
//                }
//
//
//            } catch (e: Exception) {
//                // Handle exceptions
//            }
//        }
//    }

}
