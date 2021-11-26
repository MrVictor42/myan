package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victor.myan.model.Video
import android.content.Intent
import android.net.Uri
import android.view.View
import com.victor.myan.R
import com.victor.myan.databinding.CardviewVideoBinding

class VideosAdapter(
    private val episodeList : List<Video>
) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(val binding: CardviewVideoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            CardviewVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val image = holder.binding.image
        val episodeText = holder.binding.episodeText
        val episode = holder.binding.episode
        val title = holder.binding.title
        val airedText = holder.binding.airedText
        val aired = holder.binding.aired

        title.text = episodeList[position].title

        if(episodeList[position].imageURL.isNullOrEmpty()) {
            Glide.with(holder.itemView.context).load(R.drawable.icon_banned_youtube).into(image)
        } else {
            Glide.with(holder.itemView.context).load(episodeList[position].imageURL!!).into(image)
        }

        if(episodeList[position].episode == 0) {
            episode.visibility = View.GONE
            episodeText.visibility = View.GONE
        } else {
            episode.text = episodeList[position].episode.toString()
        }

        if(episodeList[position].aired.isNullOrEmpty()) {
            airedText.visibility = View.GONE
            aired.visibility = View.GONE
        } else {
            aired.text = episodeList[position].aired!!.substring(0,10)
        }

        title.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(episodeList[position].videoURL))
            holder.itemView.context.startActivity(browserIntent)
        }

        image.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(episodeList[position].videoURL))
            holder.itemView.context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }
}