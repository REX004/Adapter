package com.tttrfge.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tttrfge.View.recyclerview.FavaroriteAdapter
import com.tttrfge.ViewModel.FavoriteViewModel
import com.tttrfge.rickmorty.databinding.FragmentFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavaroriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAdapter = FavaroriteAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        binding.apply {
            recyclerViewFavorite.layoutManager = layoutManager
            recyclerViewFavorite.adapter = favoriteAdapter
        }
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.getFavoriteCharactersLiveData().observe(this, Observer { characters ->
            favoriteAdapter.updateCharacters(characters)
        })

        viewModel.loadFavoriteCharacters(this)

        binding.backBT.setOnClickListener {
            val intent = Intent(this, CharacterListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
