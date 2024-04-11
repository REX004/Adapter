package com.tttrfge.rickmorty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.tttrfge.Model.repository.FavoritesManager
import com.tttrfge.Data.Character
import com.tttrfge.Data.Location
import com.tttrfge.View.FavoriteActivity
import com.tttrfge.rickmorty.databinding.CharacterDetailActivityBinding

class CharacterDetailActivity : AppCompatActivity() {
    lateinit var binding: CharacterDetailActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CharacterDetailActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Получаем данные о персонаже из Intent
        val characterId = intent.getStringExtra("characterId")
        val characterNames = intent.getStringExtra("characterName")
        val characterStatus = intent.getStringExtra("characterStatus")
        val characterLocations = intent.getStringExtra("characterLocation")
        val characterGender = intent.getStringExtra("characterGender")
        val characterImage = intent.getStringExtra("Image")


        binding.backBT.setOnClickListener {
            startActivity(Intent(this, CharacterListActivity::class.java))
            finish()
        }
        // Загружаем изображение с помощью Glide
        Glide.with(this).load(characterImage).into(binding.characterImager)

        binding.apply {
            characterSpecies.text = "Gender: $characterGender"
            characterLocation.text = "Location: $characterLocations"
            characterName.text = characterNames
            Status.text = "Status: $characterStatus"
        }
        binding.favoritesBT.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }




        try {

            binding.addToFavoritesButton.setOnClickListener {
                val character = Character(
                    characterId ?: "",
                    characterNames ?: "",
                    characterStatus ?: "",
                    characterGender ?: "",
                    characterImage ?: "",
                    location = Location(characterLocations ?: "", "")
                )
                val favoriteCharacters = FavoritesManager.loadFavorites(this).toMutableList()

                if (favoriteCharacters.contains(character)) {

                    Toast.makeText(this, "Character already added to favorites", Toast.LENGTH_SHORT).show()
                } else {
                    // Если персонажа нет в списке, добавляем его
                    favoriteCharacters.add(character)
                    // Сохраняем обновленный список
                    FavoritesManager.saveFavorites(this, favoriteCharacters)
                    Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
                    Log.d("Favorites", "Character saved to favorites: $character")
                }
            }
        } catch (e: Exception) {
            Log.e("Favorites", "Error adding character to favorites", e)
            Toast.makeText(this, "Error adding to favorites", Toast.LENGTH_SHORT).show()
        }


    }
}
