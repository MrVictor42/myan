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
import com.victor.myan.model.Anime
import com.victor.myan.screens.AnimeDetailFragment

class AnimeRecommendationAdapter(val anime: MutableList<Anime>) :
    RecyclerView.Adapter<AnimeRecommendationAdapter.AnimeRecommendationHolder>() {

    class AnimeRecommendationHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image : ImageView = itemView.findViewById(R.id.list_image_adapter)
        val bundle = Bundle()

        fun bind(anime: Anime) {
            Picasso.get().load(anime.image_url).placeholder(R.drawable.placeholder).fit().into(image)

            itemView.setOnClickListener {

                val fragment = AnimeDetailFragment()
                val fragmentManager = (itemView.context as FragmentActivity).supportFragmentManager

                val bundle = Bundle()
                bundle.putString("mal_id", anime.mal_id)

                fragment.arguments = bundle

                val transaction = fragmentManager.beginTransaction().replace(R.id.content, fragment)
                transaction.commit()
                fragmentManager.beginTransaction().commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeRecommendationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_image_adapter, parent, false)
        return AnimeRecommendationHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeRecommendationHolder, position: Int) {
        holder.bind(anime[position])
    }

    override fun getItemCount(): Int {
        return anime.size
    }
}

/*

    class AnimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bottomSheetFragment = AnimeBottomSheetFragment()
        private val image : ImageView = itemView.findViewById(R.id.list_image_adapter)
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
 */