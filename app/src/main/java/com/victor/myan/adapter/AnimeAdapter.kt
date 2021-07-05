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
import com.victor.myan.modals.AnimeBottomSheetFragment
import com.victor.myan.model.Anime

class AnimeAdapter(var anime: MutableList<Anime>) :
        RecyclerView.Adapter<AnimeAdapter.AnimeHolder>() {

    class AnimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bottomSheetFragment = AnimeBottomSheetFragment()
        val image: ImageView = itemView.findViewById(R.id.list_image_adapter)
        val bundle = Bundle()

        fun bind(anime: Anime) {
            Picasso.get().load(anime.image_url).placeholder(R.drawable.placeholder).fit().into(image)
            itemView.setOnClickListener {
                bundle.putString("mal_id", anime.mal_id)
                bundle.putString("title", anime.title)
                bundle.putString("image_url", anime.image_url)
                bundle.putString("airing_start", anime.airing_start)
                bundle.putInt("episodes", anime.episodes)
                bundle.putDouble("score", anime.score)
                bundle.putString("synopsis", anime.synopsis)

                bottomSheetFragment.arguments = bundle
                bottomSheetFragment.show((itemView.context as FragmentActivity).supportFragmentManager, bottomSheetFragment.tag)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_image_adapter, parent, false)
        return AnimeHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        holder.bind(anime[position])
    }

    override fun getItemCount(): Int {
        return anime.size
    }
}