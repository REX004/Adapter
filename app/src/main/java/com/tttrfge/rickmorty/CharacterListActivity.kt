package com.tttrfge.rickmorty


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tttrfge.View.recyclerview.RickAdapter
import com.tttrfge.Model.RickAndMortyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CharacterListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: RickAdapter
    private var currentPage = 1

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(RickAndMortyApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        characterAdapter = RickAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = characterAdapter
        val searchButton: Button = findViewById(R.id.button_search)



            val btFavorites = findViewById<Button>(R.id.button_favorite)
            btFavorites.setOnClickListener {
                val intent = Intent(this, FavoriteFragment::class.java)
                startActivity(intent)
            }
        searchButton.setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivityForResult(searchIntent, SEARCH_REQUEST_CODE)

        }

        loadCharacters()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0) {
                    loadCharacters()
                }
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SEARCH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
        }
    }

    private fun loadCharacters() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { apiService.getCharacters(currentPage) }
                if (response.isSuccessful) {
                    val characters = response.body()?.results ?: emptyList()
                    if (characters.isEmpty()) {
                        showError("No characters found", this@CharacterListActivity)
                    } else {
                        characterAdapter.updateCharacters(characters)
                        currentPage++
                    }
                } else {
                    showError("Failed to load characters: ${response.code()}", this@CharacterListActivity)
                }
            } catch (e: Exception) {
                showError("Error loading characters: ${e.message}", this@CharacterListActivity)
            }
        }
    }
    private fun showError(message: String, activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Exit") { dialog, _ ->
                dialog.dismiss()
                activity.finish()
            }
            .show()
    }
    private val SEARCH_REQUEST_CODE = 1001

}