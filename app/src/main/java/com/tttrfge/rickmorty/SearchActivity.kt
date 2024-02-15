package com.tttrfge.rickmorty

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tttrfge.RickAdapter
import com.tttrfge.retrofit.RickAndMortyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


 class SearchActivity : Activity(), OnCharacterClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: RickAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(RickAndMortyApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        val searchEditText: EditText = findViewById(R.id.word_find)
        val searchButton: ImageView = findViewById(R.id.searchBT)
        recyclerView = findViewById(R.id.searchResultsRecyclerView)

        characterAdapter = RickAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = characterAdapter

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val characterName = searchEditText.text.toString()
                performSearch(characterName)
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }

        searchButton.setOnClickListener {
            val characterName = searchEditText.text.toString()
            performSearch(characterName)
        }
    }

     override fun onCharacterClick(characterId: Int) {

         val intent = Intent(this, CharacterDetailActivity::class.java)
         intent.putExtra("characterId", characterId)
         startActivity(intent)
     }

     private fun performSearch(characterName: String) {
        GlobalScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { apiService.searchCharacters(characterName) }
                if (response.isSuccessful) {
                    val characters = response.body()?.results ?: emptyList()

                    runOnUiThread {
                        if (characters.isEmpty()) {
                            showNotification("No found")
                        } else {
                            characterAdapter.updateCharacters(characters)
                            characterAdapter.notifyDataSetChanged()
                        }
                    }
                } else {

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showNotification(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

     override fun onCharacterClick(
         characterId: Int,
         characterName: String,
         characterStatus: String,
         characterGender: String
     ) {
         TODO("Not yet implemented")
     }

 }

interface OnCharacterClickListener {
    fun onCharacterClick(
        characterId: Int,
        characterName: String,
        characterStatus: String,
        characterGender: String
    )

    fun onCharacterClick(characterId: Int)
}

