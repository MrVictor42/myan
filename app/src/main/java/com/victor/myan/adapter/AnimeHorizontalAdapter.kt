package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.myan.model.Anime
import com.victor.myan.databinding.CardviewItemHomeBinding

class AnimeHorizontalAdapter(private val animeList: List<Anime>) : RecyclerView.Adapter<AnimeHorizontalAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(val binding : CardviewItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(CardviewItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val itemImage = holder.binding.image

        Glide.with(holder.itemView.context).load(animeList[position].imageURL).listener(object :
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
        }).into(itemImage)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, animeList[position].title, Toast.LENGTH_SHORT).show()
        }
    }
}