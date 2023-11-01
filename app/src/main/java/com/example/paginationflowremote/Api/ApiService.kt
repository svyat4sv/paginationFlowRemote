package com.example.paginationflowremote.Api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getAllCharacters(): List<ResultRickAndMorty>

    @GET("character")
    suspend fun getPagesOfCharacters(@Query("page")page: Int): ApiResponse

}