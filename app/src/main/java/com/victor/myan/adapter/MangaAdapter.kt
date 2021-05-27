package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Manga

class MangaAdapter(var manga : MutableList<Manga>) : RecyclerView.Adapter<MangaAdapter.MangaHolder>() {

    class MangaHolder(view : View) : RecyclerView.ViewHolder(view) {
        val image = itemView.findViewById<ImageView>(R.id.list_image_adapter)
        fun bind(manga : Manga) {
            Picasso.get().load(manga.image_url).placeholder(R.drawable.placeholder).fit().into(image)
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, manga.title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_image_adapter, parent, false)
        return MangaHolder(view)
    }

    override fun onBindViewHolder(holder: MangaHolder, position: Int) {
        holder.bind(manga[position])
    }

    override fun getItemCount(): Int {
        return manga.size
    }
}