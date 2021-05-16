package com.victor.myan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Anime

class AnimeSlideAdapter(var animeList: MutableList<Anime>) : RecyclerView.Adapter<AnimeSlideAdapter.AnimeSliderHolder>() {

    class AnimeSliderHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image = itemView.findViewById<ImageView>(R.id.anime_list_top_slide)

        fun bind(anime: Anime, holder: AnimeSliderHolder) {
            val imageUrl = anime.image_url
            Picasso.get().load(imageUrl).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeSliderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anime_slide_layout, parent, false)
        return AnimeSliderHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeSliderHolder, position: Int) {
        holder.bind(animeList[position], holder)
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}