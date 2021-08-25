package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.myan.databinding.CardviewPlaceholderBinding
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
        private val progressBar = binding.progressBarPlaceholder

        fun bind(character: Character) {
            Glide.with(itemView.context).load(character.image_url).listener(object :
                RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: com.bumptech.glide.request.target.Target<Drawable>?,
                                          isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            }).into(image)

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