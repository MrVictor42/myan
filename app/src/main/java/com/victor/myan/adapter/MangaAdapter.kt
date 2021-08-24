package com.victor.myan.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victor.myan.R
import com.victor.myan.databinding.CardviewPlaceholderBinding
import com.victor.myan.model.Manga
import com.victor.myan.screens.MangaDetailFragment

class MangaAdapter : ListAdapter<Manga, MangaAdapter.MangaHolder>(MangaAdapter) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<Manga>() {
        override fun areItemsTheSame(oldItem: Manga, newItem: Manga): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Manga, newItem: Manga): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }
    }

    inner class MangaHolder(binding: CardviewPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.imagePlaceholder

        fun bind(manga: Manga) {
            Glide.with(itemView.context).load(manga.image_url).into(image)

            itemView.setOnClickListener {
                val fragment = MangaDetailFragment()
                val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager

                val bundle = Bundle()
                bundle.putString("mal_id", manga.mal_id)

                fragment.arguments = bundle

                val transaction = fragmentManager?.beginTransaction()?.replace(R.id.content, fragment)
                transaction?.commit()
                fragmentManager?.beginTransaction()?.commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaHolder {
        return MangaHolder(
            CardviewPlaceholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: MangaHolder, position: Int) {
        val manga = getItem(position)
        holder.bind(manga)
    }
}