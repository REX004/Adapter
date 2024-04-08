package com.tttrfge.Model.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tttrfge.Data.Character

object FavoritesManager {

        private const val PREF_NAME = "FavoritesPref"
        private const val KEY_FAVORITES = "favorites"

        fun loadFavorites(context: Context): MutableList<Character> {
            val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val gson = Gson()
            val json = prefs.getString(KEY_FAVORITES, null)
            val type = object : TypeToken<MutableList<Character>>() {}.type
            return gson.fromJson(json, type) ?: mutableListOf()
        }

        fun saveFavorites(context: Context, favorites: MutableList<Character>) {
            val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()
            val gson = Gson()
            val json = gson.toJson(favorites)
            editor.putString(KEY_FAVORITES, json)
            editor.apply()
        }
    }
