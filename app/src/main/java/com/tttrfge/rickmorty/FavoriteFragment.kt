package com.tttrfge.rickmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tttrfge.View.recyclerview.FavaroriteAdapter
import com.tttrfge.Model.FavoritesManager

class FavoriteFragment : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavaroriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_favorite)

        recyclerView = findViewById(R.id.recyclerViewFavorite)
        favoriteAdapter = FavaroriteAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = favoriteAdapter

        // Получаем список избранных персонажей из SharedPreferences
        val favoriteCharacters = FavoritesManager.loadFavorites(this)

        // Обновляем данные в адаптере
        favoriteAdapter.updateCharacters(favoriteCharacters)
    }
}
