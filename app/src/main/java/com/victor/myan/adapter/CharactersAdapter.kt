package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.myan.R
import com.victor.myan.model.Character
import com.victor.myan.baseFragments.BaseCharacterFragment
import com.victor.myan.databinding.CardviewPlaceholderBinding
import com.victor.myan.helper.DiffUtilHelper

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private var characterList = emptyList<Character>()

    inner class CharacterViewHolder(val binding: CardviewPlaceholderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(CardviewPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val image = holder.binding.image
        val name = holder.binding.name

        Glide.with(holder.itemView.context).load(characterList[position].imageURL).listener(object :
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
        name.text = characterList[position].name

        holder.itemView.setOnClickListener {
            val fragment = BaseCharacterFragment(characterList[position].malID)
            val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

            val transaction = fragmentManager?.beginTransaction()?.replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
            transaction?.commit()
            fragmentManager?.beginTransaction()?.commit()
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun setData(newCharacterList : List<Character>) {
        val diffUtil = DiffUtilHelper(characterList, newCharacterList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        characterList = newCharacterList
        diffResults.dispatchUpdatesTo(this)
    }
}