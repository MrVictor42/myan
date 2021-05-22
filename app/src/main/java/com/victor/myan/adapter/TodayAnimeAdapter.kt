package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Anime

class TodayAnimeAdapter(var anime: MutableList<Anime>) :
        RecyclerView.Adapter<TodayAnimeAdapter.TodayAnimeHolder>() {

    class TodayAnimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val anime_image = itemView.findViewById<ImageView>(R.id.today_anime_imageView)
        fun bind(anime: Anime) {
            Picasso.get().load(anime.image_url).placeholder(R.drawable.placeholder).fit().into(anime_image)
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, anime.title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayAnimeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anime_image, parent, false)
        return TodayAnimeHolder(view)
    }

    override fun onBindViewHolder(holder: TodayAnimeHolder, position: Int) {
        holder.bind(anime[position])
    }

    override fun getItemCount(): Int {
        return anime.size
    }
}