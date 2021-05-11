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

class TodayAnimeAdapter(var items: MutableList<Anime>) : RecyclerView.Adapter<TodayAnimeAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageurl = view.findViewById<ImageView>(R.id.image_url)
        fun bind(anime: Anime, holder: MyViewHolder) {
            val imageUrl = anime.image_url
            Picasso.get().load(imageUrl).into(imageurl)

            holder.itemView.setOnClickListener {
                Toast.makeText(holder.itemView.context, anime.title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position], holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}