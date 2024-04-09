package com.tttrfge.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tttrfge.Data.Character
import com.tttrfge.Model.repository.FavoritesManager

class FavoriteViewModel: ViewModel() {
    private val favoriteCharactersLiveData = MutableLiveData<List<Character>>()
    fun getFavoriteCharactersLiveData(): LiveData<List<Character>>{
        return favoriteCharactersLiveData
    }
    fun loadFavoriteCharacters(context: Context){
        val favoriteCharacters = FavoritesManager.loadFavorites(context)
        favoriteCharactersLiveData.value = favoriteCharacters
    }
}