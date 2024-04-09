package com.tttrfge.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tttrfge.View.recyclerview.RickAdapter
import com.tttrfge.ViewModel.CharacterListViewModel
import com.tttrfge.rickmorty.SearchActivity
import com.tttrfge.rickmorty.databinding.ActivityMainBinding
class CharacterListActivity : AppCompatActivity() {

    private lateinit var viewModel: CharacterListViewModel
    private var currentPage = 1
    private val characterAdapter = RickAdapter(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CharacterListViewModel::class.java]

        binding.buttonFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        binding.buttonSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = characterAdapter

        viewModel.charactersLiveData.observe(this, Observer { characters ->
            if (characters.isNotEmpty()) {
                characterAdapter.updateCharacters(characters)
                currentPage++
            } else {
                showError("No characters found")
            }
        })

        viewModel.errorMessageLiveData.observe(this, Observer { errorMessage ->
            showError(errorMessage)
        })

        loadCharacters()
    }

    private fun loadCharacters() {
        viewModel.loadCharacters(currentPage)
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Exit") { dialog, _ -> finish() }
            .show()
    }
}