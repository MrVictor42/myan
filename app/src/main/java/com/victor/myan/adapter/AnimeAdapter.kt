package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.victor.myan.R
import com.victor.myan.model.Anime
import com.victor.myan.baseFragments.BaseAnimeDetailFragment
import com.victor.myan.databinding.CardviewPlaceholderHorizontalBinding
import com.victor.myan.helper.DiffUtilAnimeHelper

class AnimeAdapter : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    private var animeList = emptyList<Anime>()

    inner class AnimeViewHolder(val binding : CardviewPlaceholderHorizontalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(CardviewPlaceholderHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val image = holder.binding.image

        Glide.with(holder.itemView.context).load(animeList[position].imageUrl).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }).into(image)

        holder.itemView.setOnClickListener {
            val fragment = BaseAnimeDetailFragment()
            val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

            val bundle = Bundle()
            bundle.putInt("mal_id", animeList[position].malID)

            fragment.arguments = bundle

            val transaction =
                fragmentManager?.
                beginTransaction()?.
                replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
            transaction?.commit()
            fragmentManager?.beginTransaction()?.commit()
        }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    fun setData(newAnimeList : List<Anime>) {
        val diffUtil = DiffUtilAnimeHelper(animeList, newAnimeList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        animeList = newAnimeList
        diffResults.dispatchUpdatesTo(this)
    }
}