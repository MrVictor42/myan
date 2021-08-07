package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Character

class CharactersAdapter(var character: MutableList<Character>) :
    RecyclerView.Adapter<CharactersAdapter.CharacterHolder>() {

    class CharacterHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image : ImageView = itemView.findViewById(R.id.list_image_adapter)

        fun bind(character: Character) {
            Picasso.get().load(character.image_url).placeholder(R.drawable.placeholder).fit().into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_image_adapter, parent, false)
        return CharacterHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.bind(character[position])
    }

    override fun getItemCount(): Int {
        return character.size
    }
}