package com.victor.myan.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.victor.myan.R
import com.victor.myan.modals.MangaBottomSheetFragment
import com.victor.myan.model.Manga

class MangaAdapter(var manga : MutableList<Manga>) : RecyclerView.Adapter<MangaAdapter.MangaHolder>() {

    class MangaHolder(view : View) : RecyclerView.ViewHolder(view) {
        val bottomSheetFragment = MangaBottomSheetFragment()
        val image = itemView.findViewById<ImageView>(R.id.list_image_adapter)
        val bundle = Bundle()

        fun bind(manga : Manga) {
            Picasso.get().load(manga.image_url).placeholder(R.drawable.placeholder).fit().into(image)
            itemView.setOnClickListener {
                bundle.putString("mal_id", manga.mal_id)
                bundle.putString("title", manga.title)
                bundle.putString("image_url", manga.image_url)
                bundle.putString("start_date", manga.start_date)
                bundle.putInt("volumes", manga.volumes)
                bundle.putDouble("score", manga.score)

                bottomSheetFragment.arguments = bundle
                bottomSheetFragment.show((itemView.context as FragmentActivity).supportFragmentManager, bottomSheetFragment.tag)
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