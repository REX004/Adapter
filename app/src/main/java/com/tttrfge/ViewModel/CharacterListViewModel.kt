package com.tttrfge.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tttrfge.Data.Character
import com.tttrfge.Model.repository.Repository
import kotlinx.coroutines.launch

class CharacterListViewModel : ViewModel() {

    private val repository = Repository()

    val charactersLiveData = MutableLiveData<List<Character>>()
    val errorMessageLiveData = MutableLiveData<String>()

    fun loadCharacters(page: Int) {
        viewModelScope.launch {
            try {
                val characters = repository.getCharacters(page)
                charactersLiveData.postValue(characters)
            } catch (e: Exception) {
                errorMessageLiveData.postValue("Error loading characters: ${e.message}")
            }
        }
    }
}