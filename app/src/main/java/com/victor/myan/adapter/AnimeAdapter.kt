package com.victor.myan.adapter

import android.content.Context
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
import com.victor.myan.model.Anime
import com.victor.myan.baseFragments.BaseAnimeDetailFragment
import com.victor.myan.databinding.CardviewPlaceholderHorizontalBinding

class AnimeAdapter : ListAdapter<Anime, AnimeAdapter.AnimeHolder>(MyDiffUtil) {

    private lateinit var context : Context

    companion object MyDiffUtil : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
            return oldItem.malID == newItem.malID
        }
    }

    inner class AnimeHolder(binding: CardviewPlaceholderHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.image

        fun bind(anime: Anime) {
            context = itemView.context
            Glide.with(itemView.context).load(anime.imageUrl).listener(object :
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
                render(anime.malID)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        return AnimeHolder(
            CardviewPlaceholderHorizontalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        val anime = getItem(position)
        holder.bind(anime)
    }

    private fun render(malID: Int) {
        val fragment = BaseAnimeDetailFragment()
        val fragmentManager = (context as FragmentActivity?)?.supportFragmentManager

        val bundle = Bundle()
        bundle.putInt("mal_id", malID)

        fragment.arguments = bundle

        val transaction =
            fragmentManager?.
            beginTransaction()?.
            replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
        transaction?.commit()
        fragmentManager?.beginTransaction()?.commit()
    }
}