package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Anime

class TodayAnimeAdapter(var anime: MutableList<Anime>) :
        RecyclerView.Adapter<TodayAnimeAdapter.TodayAnimeHolder>() {

    class TodayAnimeHolder(view: View) : RecyclerView.ViewHolder(view) {

        val animeImage = itemView.findViewById<ImageView>(R.id.today_anime_imageView)

        fun bind(anime: Anime, holder: TodayAnimeAdapter.TodayAnimeHolder) {
            Picasso.get().load(anime.image_url).placeholder(R.drawable.placeholder).fit().into(animeImage)
            itemView.setOnClickListener {
                Toast.makeText(holder.itemView.context, anime.title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayAnimeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.today_anime_category, parent, false)
        return TodayAnimeHolder(view)
    }

    override fun onBindViewHolder(holder: TodayAnimeHolder, position: Int) {
        holder.bind(anime[position], holder)
    }

    override fun getItemCount(): Int {
        return anime.size
    }
}