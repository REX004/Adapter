package com.tttrfge.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tttrfge.Data.Character
import com.tttrfge.Model.Apis.RickAndMortyApi
import com.tttrfge.rickmorty.databinding.SearchActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val apiService: RickAndMortyApi): ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters


    fun searchCharacters(characterName: String){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { apiService.searchCharacters(characterName) }
                if (response.isSuccessful) {
                    _characters.value = response.body()?.results ?: emptyList()
                } else {

                }
            }catch (e: Exception){
                e.printStackTrace()

            }

        }

    }



}