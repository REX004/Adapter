//package com.tttrfge.ViewModel
//
//import android.util.Log
//import android.widget.Toast
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.tttrfge.Data.Character
//import com.tttrfge.Data.Location
//import com.tttrfge.Model.repository.FavoritesManager
//import kotlinx.coroutines.launch
//
//class CharacterDetailViewModel : ViewModel() {
//
//    fun addToFavorites(
//    characterId: String,
//    characterNames : String,
//    characterStatus: String,
//    characterGender : String,
//    characterImage : String,
//    characterLocation : String
//    ) {
//        val character = Character(
//            characterId,
//            characterNames,
//            characterStatus,
//            characterGender,
//            characterImage,
//            location = Location(characterLocation, "")
//        )
//        val favoriteCharacters = FavoritesManager.loadFavorites(this).toMutableList()
//        if (favoriteCharacters.contains(character)){
//            Toast.makeText(this, "Character already added to favorites", Toast.LENGTH_SHORT).show()
//        }else{
//            favoriteCharacters.add(character)
//            FavoritesManager.saveFavorites(this, favoriteCharacters)
//            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
//            Log.d("Favorites", "Character saved to favorites: $character")
//
//        }
//
//
//    }
//}