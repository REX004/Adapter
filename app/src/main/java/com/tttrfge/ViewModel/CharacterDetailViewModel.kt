//package com.tttrfge.ViewModel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.tttrfge.Model.Character
//import com.tttrfge.Model.FavoritesManager
//import kotlinx.coroutines.launch
//
//class CharacterDetailViewModel : ViewModel() {
//
//    fun addToFavorites(character: Character) {
//        viewModelScope.launch {
//            val favoriteCharacters = FavoritesManager.loadFavorites()
//            if (favoriteCharacters.contains(character)) {
//                // Если персонаж уже в списке, можно обработать это здесь
//            } else {
//                favoriteCharacters.add(character)
//                FavoritesManager.saveFavorites(favoriteCharacters)
//            }
//        }
//    }
//}
