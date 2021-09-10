package com.victor.myan.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Genre
import com.victor.myan.screens.GenreFragment

class GenresAdapter(var genres: MutableList<Genre>) :
    RecyclerView.Adapter<GenresAdapter.CatalogHolder>() {

    class CatalogHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image : ImageView = itemView.findViewById(R.id.imageCatalog)
        private val textCatalog : TextView = itemView.findViewById(R.id.textCatalog)

        fun bind(genre : Genre) {
            textCatalog.text = genre.name

            Picasso.get().load(genre.image).placeholder(R.drawable.placeholder).fit().into(image)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("mal_id", genre.mal_id)
                bundle.putString("name", genre.name)

                val fragment = GenreFragment()
                fragment.arguments = bundle
                (itemView.context as FragmentActivity)
                    .supportFragmentManager
                    .beginTransaction().replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
                    .commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_category, parent, false)
        return CatalogHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}