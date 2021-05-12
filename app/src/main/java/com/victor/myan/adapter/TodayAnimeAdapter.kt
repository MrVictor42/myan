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
import com.victor.myan.fragments.AnimeModalFragment
import com.victor.myan.model.Anime

class TodayAnimeAdapter(var items: MutableList<Anime>) : RecyclerView.Adapter<TodayAnimeAdapter.TodayAnimeHolder>() {

    class TodayAnimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bottomSheetDialogFragment = AnimeModalFragment()
        val imageurl = view.findViewById<ImageView>(R.id.image_url)
        val bundle = Bundle()

        fun bind(anime: Anime, holder: TodayAnimeHolder) {
            val imageUrl = anime.image_url
            Picasso.get().load(imageUrl).into(imageurl)

            holder.itemView.setOnClickListener {
                bundle.putString("image_url", anime.image_url)
                bundle.putString("airing_start", anime.airing_start.substring(0,4))
                bundle.putString("title", anime.title)
                bundle.putInt("episodes", anime.episodes)
                bundle.putDouble("score", anime.score)
                bundle.putString("synopsis", anime.synopsis)

                bottomSheetDialogFragment.arguments = bundle
                bottomSheetDialogFragment.show((holder.itemView.context as FragmentActivity).supportFragmentManager, bottomSheetDialogFragment.tag)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayAnimeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_adapter, parent, false)
        return TodayAnimeHolder(view)
    }

    override fun onBindViewHolder(holder: TodayAnimeHolder, position: Int) {
        holder.bind(items[position], holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}