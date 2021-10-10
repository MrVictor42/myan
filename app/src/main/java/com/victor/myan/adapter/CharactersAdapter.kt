package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.myan.R
import com.victor.myan.model.Character
import com.victor.myan.baseFragments.BaseCharacterDetailFragment
import com.victor.myan.databinding.CardviewPlaceholderHorizontalBinding
import com.victor.myan.databinding.CardviewPlaceholderVerticalBinding

class CharactersAdapter : ListAdapter<Character, CharactersAdapter.CharacterHolder>(MyDiffUtil) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.malID == newItem.malID
        }
    }

    inner class CharacterHolder(binding: CardviewPlaceholderVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.image
        private val name = binding.name

        fun bind(character: Character) {
            Glide.with(itemView.context).load(character.imageUrl).listener(object :
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
                    return false
                }
            }).into(image)
            name.text = character.name

            itemView.setOnClickListener {
                val fragment = BaseCharacterDetailFragment()
                val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager

                val bundle = Bundle()
                bundle.putInt("mal_id", character.malID)

                fragment.arguments = bundle

                val transaction = fragmentManager?.beginTransaction()?.replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
                transaction?.commit()
                fragmentManager?.beginTransaction()?.commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        return CharacterHolder(
            CardviewPlaceholderVerticalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: CharactersAdapter.CharacterHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }
}