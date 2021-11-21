package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.databinding.ItemHomeFragmentBinding
import com.victor.myan.model.Categories

class RecyclerViewVerticalAdapter(
    private val categoryList : MutableList<Categories>
) : RecyclerView.Adapter<RecyclerViewVerticalAdapter.VerticalHolder>() {

    inner class VerticalHolder(val binding : ItemHomeFragmentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalHolder {
        return VerticalHolder(ItemHomeFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VerticalHolder, position: Int) {
        val recyclerViewHorizontal = holder.binding.recyclerviewHorizontal
        val title = holder.binding.titleItem

        title.text = categoryList[position].title
        recyclerViewHorizontal.layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.HORIZONTAL, false)
        recyclerViewHorizontal.adapter = RecyclerViewHorizontalAdapter(categoryList[position].categories, categoryList[position].type)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}