package com.tttrfge

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tttrfge.retrofit.Character
import com.tttrfge.rickmorty.CharacterDetailActivity
import com.tttrfge.rickmorty.R


class RickAdapter(private val context: Context)
    : RecyclerView.Adapter<RickAdapter.RickViewHolder>() {

    private val characters: MutableList<Character> = mutableListOf()


    @SuppressLint("NotifyDataSetChanged")
    fun updateCharacters(newCharacters: List<Character>) {
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
        private val characterName: TextView = itemView.findViewById(R.id.name_person)
        private val characterStatus: TextView = itemView.findViewById(R.id.status_person)
        private val characterGender: TextView = itemView.findViewById(R.id.gender)
        private val image: ImageView = itemView.findViewById(R.id.im_person)


        @SuppressLint("SetTextI18n")
        fun bind(character: Character) {
            characterName.text = character.name
            characterStatus.text = "Status: ${character.status}"
            characterGender.text = "Gender: ${character.gender}"


            Glide.with(itemView.context)
                .load(character.image)
                .placeholder(R.drawable.rick)
                .into(image)

            image.setOnClickListener {

                val intent = Intent(context, CharacterDetailActivity::class.java)
                context.startActivity(intent)
            }

        }

    }
    interface CharacterClickListener {
        fun onCharacterClick(character: CharacterEntity)
    }

    class CharacterAdapter(private val clickListener: CharacterClickListener) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

        private var characters: List<CharacterEntity> = emptyList()

        @SuppressLint("NotifyDataSetChanged")
        fun updateCharacters(newCharacters: List<CharacterEntity>) {
            characters = newCharacters
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.character_detail_activity, parent, false)
            return CharacterViewHolder(view)
        }


        override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

            val character = characters[position]
            holder.bind()

            holder.itemView.setOnClickListener {

                val intent = Intent(holder.itemView.context, CharacterDetailActivity::class.java)
                intent.putExtra("characterId", character.id)
                intent.putExtra("characterName", character.name)
                intent.putExtra("characterStatus", character.status)
                holder.itemView.context.startActivity(intent)
            }

            holder.itemView.setOnClickListener {
                Log.d("CharacterAdapter", "Item clicked: ${character.name}")

                val intent = Intent(holder.itemView.context, CharacterDetailActivity::class.java)


                holder.itemView.context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int = characters.size

        class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind() {
            }
        }
    }
    interface OnCharacterClickListener {
        fun onCharacterClick(characterId: Int)
    }

}
