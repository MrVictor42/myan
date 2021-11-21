package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.databinding.ItemHomeFragmentBinding
import com.victor.myan.helper.DiffUtilHelper
import com.victor.myan.model.Anime

class AnimeRecyclerViewVerticalAdapter : RecyclerView.Adapter<AnimeRecyclerViewVerticalAdapter.VerticalHolder>() {

    private var animeList = emptyList<Anime>()

    inner class VerticalHolder(val binding : ItemHomeFragmentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalHolder {
        return VerticalHolder(ItemHomeFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VerticalHolder, position: Int) {
        val recyclerViewHorizontal = holder.binding.recyclerviewHorizontal
        val title = holder.binding.titleItem

        title.text = "Sunday"
        recyclerViewHorizontal.layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.HORIZONTAL, false)
        recyclerViewHorizontal.adapter = AnimeHorizontalAdapter(animeList)
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