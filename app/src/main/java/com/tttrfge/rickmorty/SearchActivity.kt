package com.tttrfge.rickmorty

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tttrfge.View.recyclerview.RickAdapter
import com.tttrfge.Model.Apis.RickAndMortyApi
import com.tttrfge.View.CharacterListActivity
import com.tttrfge.ViewModel.SearchViewModel
import com.tttrfge.rickmorty.databinding.SearchActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : Activity(), OnCharacterClickListener {

     private lateinit var viewModel: SearchViewModel
     private lateinit var recyclerView: RecyclerView
     private lateinit var characterAdapter: RickAdapter
     private lateinit var binding: SearchActivityBinding

     private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(RickAndMortyApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.searchResultsRecyclerView)

        characterAdapter = RickAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = characterAdapter

        binding.wordFind.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val characterName = binding.wordFind.text.toString()
                performSearch(characterName)
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }
        binding.backBT.setOnClickListener {
            val intent = Intent(this, CharacterListActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.searchBT.setOnClickListener {
            val characterName = binding.wordFind.text.toString()
            if (isNetworkConnected()) {
                performSearch(characterName)
            } else {
                showNoInternetDialog(this)
            }
        }
    }
     private fun isNetworkConnected(): Boolean {
         val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val activeNetworkInfo = connectivityManager.activeNetworkInfo
         return activeNetworkInfo != null && activeNetworkInfo.isConnected
     }

     private fun showNoInternetDialog(activity: Activity) {
         AlertDialog.Builder(this)
             .setTitle("No Internet Connection")
             .setMessage("Please check your internet connection and try again.")
             .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
             .setNegativeButton("Go Back") {dialog, _ ->
                 dialog.dismiss()
                 activity.finish()
             }
             .show()
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