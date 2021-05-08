package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Anime

class TodayAnimeAdapter(var items: MutableList<Anime>) : RecyclerView.Adapter<TodayAnimeAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageurl = view?.findViewById<ImageView>(R.id.image_url)
        val title = view?.findViewById<TextView>(R.id.title)
        fun bind(anime: Anime) {
            val image_url = anime.image_url
            title.text = anime.title
            Picasso.get().load(image_url).into(imageurl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}