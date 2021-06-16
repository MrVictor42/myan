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
import com.victor.myan.fragments.CategoryDetailFragment
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
                val bundle = Bundle()
                bundle.putInt("genre", category.genre)
                bundle.putString("type", category.type)

                val categoryController = CategoryDetailFragment()
                categoryController.arguments = bundle
                (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, categoryController).addToBackStack(null).commit()
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