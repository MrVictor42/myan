package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victor.myan.databinding.CardviewPlaceholderBinding
import com.victor.myan.databinding.ListImageAdapterBinding
import com.victor.myan.model.Character

class CharactersAdapter : ListAdapter<Character, CharactersAdapter.CharacterHolder>(MyDiffUtil) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }
    }

    inner class CharacterHolder(binding: CardviewPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.imagePlaceholder

        fun bind(character: Character) {
            Glide.with(itemView.context).load(character.image_url).into(image)

            image.setOnClickListener {
                Toast.makeText(itemView.context, character.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        return CharacterHolder(
            CardviewPlaceholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: CharactersAdapter.CharacterHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }
}