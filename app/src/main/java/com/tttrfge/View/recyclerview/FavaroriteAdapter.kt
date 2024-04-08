package com.tttrfge.View.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tttrfge.Model.Character
import com.tttrfge.rickmorty.R
import com.tttrfge.rickmorty.databinding.ItemListFavoriteBinding

class FavaroriteAdapter(private val context: Context) : RecyclerView.Adapter<FavaroriteAdapter.FavoriteViewHolder>() {

    private val characters: MutableList<Character> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun updateCharacters(newCharacters: List<Character>) {
        characters.clear()
        characters.addAll(newCharacters)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemListFavoriteBinding.bind(itemView)

        fun bind(character: Character) = with(binding){
            namePerson.text = character.name
            statusPerson.text = "Status: ${character.status}"
            gender.text = "Gender: ${character.gender}"
            locationName.text = "Location: ${character.location.name}"

            Glide.with(context)
                .load(character.image)
                .placeholder(R.drawable.rick)
                .into(binding.imPerson)
        }
    }
}
