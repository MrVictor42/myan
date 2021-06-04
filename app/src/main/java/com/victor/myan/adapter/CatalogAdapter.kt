package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.picassopalette.PicassoPalette
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.enums.AnimeGenreEnum

class CatalogAdapter(var animeGenreEnum: MutableList<AnimeGenreEnum>) :
    RecyclerView.Adapter<CatalogAdapter.CatalogHolder>() {

    class CatalogHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView = itemView.findViewById(R.id.imageCatalog)
        val textCatalog : TextView = itemView.findViewById(R.id.textCatalog)
        fun bind(animeGenreEnum: AnimeGenreEnum) {
            textCatalog.text = animeGenreEnum.name
            Picasso
                .get().load(animeGenreEnum.image).placeholder(R.drawable.placeholder)
                .fit().into(image,
                    PicassoPalette.with(animeGenreEnum.image.toString(), image)
                        .use(PicassoPalette.Profile.VIBRANT_LIGHT)
                        .intoBackground(image, PicassoPalette.Swatch.RGB))
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, animeGenreEnum.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_catalog, parent, false)
        return CatalogHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogHolder, position: Int) {
        holder.bind(animeGenreEnum[position])
    }

    override fun getItemCount(): Int {
        return animeGenreEnum.size
    }
}