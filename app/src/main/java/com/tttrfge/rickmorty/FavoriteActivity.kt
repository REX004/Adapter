package com.tttrfge.rickmorty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tttrfge.View.recyclerview.FavaroriteAdapter
import com.tttrfge.Model.repository.FavoritesManager
import com.tttrfge.View.CharacterListActivity
import com.tttrfge.rickmorty.databinding.FragmentFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavaroriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerViewFavorite)
        favoriteAdapter = FavaroriteAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = favoriteAdapter

        // Получаем список избранных персонажей из SharedPreferences
        val favoriteCharacters = FavoritesManager.loadFavorites(this)

        // Обновляем данные в адаптере
        favoriteAdapter.updateCharacters(favoriteCharacters)

        binding.backBT.setOnClickListener {
            val intent = Intent(this, CharacterListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
