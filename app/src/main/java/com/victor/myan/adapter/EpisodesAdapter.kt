package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victor.myan.databinding.CardviewEpisodesBinding
import com.victor.myan.model.Episode
import android.content.Intent
import android.net.Uri

class EpisodesAdapter(
    private val episodeList : List<Episode>
) : RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>() {

    inner class EpisodeViewHolder(val binding: CardviewEpisodesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            CardviewEpisodesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val image = holder.binding.image
        val episode = holder.binding.episode
        val title = holder.binding.title

        Glide.with(holder.itemView.context).load(episodeList[position].imageURL).into(image)
        episode.text = episodeList[position].episode
        title.text = episodeList[position].title

        title.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(episodeList[position].url))
            holder.itemView.context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }
}