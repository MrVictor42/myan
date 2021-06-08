package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.picassopalette.PicassoPalette
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.model.Category

class CategoryAdapter(var categories: MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CatalogHolder>() {

    class CatalogHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView = itemView.findViewById(R.id.imageCatalog)
        val textCatalog : TextView = itemView.findViewById(R.id.textCatalog)
        fun bind(category: Category) {
            textCatalog.text = category.type
            Picasso.get().load(category.image).placeholder(R.drawable.placeholder).fit().into(image)
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, category.type, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_category, parent, false)
        return CatalogHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}