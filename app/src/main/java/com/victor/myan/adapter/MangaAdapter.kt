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
import com.victor.myan.databinding.CardviewPlaceholderHorizontalBinding
import com.victor.myan.databinding.CardviewPlaceholderVerticalBinding
import com.victor.myan.model.Manga
import com.victor.myan.screens.MangaDetailFragment

class MangaAdapter : ListAdapter<Manga, MangaAdapter.MangaHolder>(MangaAdapter) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<Manga>() {
        override fun areItemsTheSame(oldItem: Manga, newItem: Manga): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Manga, newItem: Manga): Boolean {
            return oldItem.malID == newItem.malID
        }
    }

    inner class MangaHolder(binding: CardviewPlaceholderHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.image

        fun bind(manga: Manga) {
            Glide.with(itemView.context).load(manga.imageUrl).listener(object :
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

            itemView.setOnClickListener {
                val fragment = MangaDetailFragment()
                val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager

                val bundle = Bundle()
                bundle.putInt("mal_id", manga.malID)

                fragment.arguments = bundle

                val transaction = fragmentManager?.beginTransaction()?.replace(R.id.fragment_layout, fragment)
                transaction?.commit()
                fragmentManager?.beginTransaction()?.commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaHolder {
        return MangaHolder(
            CardviewPlaceholderHorizontalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: MangaHolder, position: Int) {
        val manga = getItem(position)
        holder.bind(manga)
    }
}