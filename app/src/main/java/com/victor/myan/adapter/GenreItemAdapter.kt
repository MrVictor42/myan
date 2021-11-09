package com.victor.myan.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseGenreDetailFragment
import com.victor.myan.databinding.CardviewCircleBinding
import com.victor.myan.model.Genre

class GenreItemAdapter(private val genreList : List<Genre>) : RecyclerView.Adapter<GenreItemAdapter.GenreItemViewHolder>() {

    inner class GenreItemViewHolder(val binding : CardviewCircleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreItemViewHolder {
        return GenreItemViewHolder(CardviewCircleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) {
        val genreType = holder.binding.itemNameText

        genreType.text = genreList[position].name
        holder.itemView.setOnClickListener {
            val fragment = BaseGenreDetailFragment()
            val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

            val bundle = Bundle()
            bundle.putString("name", genreList[position].name)
            bundle.putInt("mal_id", genreList[position].malID)

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
        return genreList.size
    }
}