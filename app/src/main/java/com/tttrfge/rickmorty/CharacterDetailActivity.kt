package com.tttrfge.rickmorty

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.tttrfge.CharacterDatabase
import com.tttrfge.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_detail_activity)

        val characterName = intent.getStringExtra("characterName")
        val characterStatus = intent.getStringExtra("characterStatus")
        val characterSpecies = intent.getStringExtra("characterSpecies")

        val addToFavoriteButton: Button = findViewById(R.id.addToFavoritesButton)

        addToFavoriteButton.setOnClickListener {
            val characterId = intent.getIntExtra("characterId", -1)
            val characterName = intent.getStringExtra("characterName") ?: ""
            val characterStatus = intent.getStringExtra("characterStatus") ?: ""
            val characterSpecies = intent.getStringExtra("characterSpecies") ?: ""

            val characterEntity = CharacterEntity(
                id = characterId,
                name = characterName,
                status = characterStatus,
                species = characterSpecies
            )

            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    CharacterDatabase.characterDao().insertCharacter(characterEntity)
                }
            }
            }
        val characterDetailImage: ImageView = findViewById(R.id.characterImage)
        val characterDetailName: TextView = findViewById(R.id.characterName)
        val characterDetailStatus: TextView = findViewById(R.id.Status)
        val characterDetailSpecies: TextView = findViewById(R.id.characterSpecies)

        Glide.with(this)
            .load(R.drawable.ic_launcher_foreground)
            .into(characterDetailImage)

        characterDetailName.text = characterName
        characterDetailStatus.text = "Status: $characterStatus"
        characterDetailSpecies.text = "Species: $characterSpecies"

    }
    private fun Any.
            insertCharacter(characterEntity: CharacterEntity) {
    }

}

