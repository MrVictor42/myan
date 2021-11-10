package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import com.victor.myan.baseFragments.BaseAnimeFragment
import com.victor.myan.databinding.CardviewPlaceholderBinding
import com.victor.myan.helper.DiffUtilHelper

class AnimeAdapter : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    private var animeList = emptyList<Anime>()

    inner class AnimeViewHolder(val binding : CardviewPlaceholderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(CardviewPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val image = holder.binding.image
        holder.binding.name.visibility = View.GONE

        Glide.with(holder.itemView.context).load(animeList[position].imageURL).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }).into(image)

        holder.itemView.setOnClickListener {
            val fragment = BaseAnimeFragment()
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
        val diffUtil = DiffUtilHelper(animeList, newAnimeList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        animeList = newAnimeList
        diffResults.dispatchUpdatesTo(this)
    }
}