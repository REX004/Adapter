package com.tttrfge.Model

import com.tttrfge.Model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterResponse>
    @GET("character")
    suspend fun searchCharacters(@Query("name") name: String): Response<CharacterResponse>
}