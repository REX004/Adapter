package com.tttrfge.View.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tttrfge.Data.Character
import com.tttrfge.rickmorty.CharacterDetailActivity
import com.tttrfge.rickmorty.R
import com.tttrfge.rickmorty.databinding.ItemListBinding


class RickAdapter(private val context: Context)
    : RecyclerView.Adapter<RickAdapter.RickViewHolder>() {

    private val characters: MutableList<Character> = mutableListOf()

    private var lastLoadedIndex = 0
    @SuppressLint("NotifyDataSetChanged")
    fun updateCharacters(newCharacters: List<Character>) {
        lastLoadedIndex = characters.size
        characters.addAll(newCharacters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RickViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_list, parent, false)
        return RickViewHolder(view)
    }

    override fun onBindViewHolder(holder: RickViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int {
        Log.d("RickAdapter", "getItemCount: ${characters.size}")
        return characters.size
    }

    inner class RickViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemListBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(character: Character) = with(binding) {
            namePerson.text = character.name
            statusPerson.text = "Status: ${character.status}"
            gender.text = "Gender: ${character.gender}"
            locationName.text = "Location: ${character.location.name}"


            Glide.with(itemView.context)
                .load(character.image)
                .placeholder(R.drawable.rick)
                .into(imPerson)

            imPerson.setOnClickListener {
                val intent = Intent(context, CharacterDetailActivity::class.java)
                intent.putExtra("characterId", character.id)
                intent.putExtra("characterName", character.name)
                intent.putExtra("characterStatus", character.status)
                intent.putExtra("characterGender", character.gender)
                intent.putExtra("characterLocation", character.location.name)
                intent.putExtra("Image", character.image)
                context.startActivity(intent)
            }

        }

    }
}
