package com.tttrfge.Model.repository

import com.tttrfge.Data.Character
import com.tttrfge.Model.Apis.RickAndMortyApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(RickAndMortyApi::class.java)

    suspend fun getCharacters(page: Int): List<Character> {
        val response = apiService.getCharacters(page)
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        } else {
            throw Exception("Failed to load characters: ${response.code()}")
        }
    }
}
