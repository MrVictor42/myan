package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseGenreDetailFragment
import com.victor.myan.databinding.CardviewPlaceholderGenreBinding
import com.victor.myan.model.Genre

class GenreAdapter : ListAdapter<Genre, GenreAdapter.GenreHolder>(MyDiffUtil) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.malID == newItem.malID
        }
    }

    inner class GenreHolder(binding : CardviewPlaceholderGenreBinding)
        : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.image
        private val title = binding.title
        private val progressBar = binding.progressBar

        fun bind(genre: Genre) {
            Glide.with(itemView.context).load(genre.image).listener(object :
                RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: com.bumptech.glide.request.target.Target<Drawable>?,
                                          isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            }).into(image)
            title.text = genre.name

            itemView.setOnClickListener {
                val fragment = BaseGenreDetailFragment()
                val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager

                val bundle = Bundle()
                bundle.putInt("mal_id", genre.malID)

                fragment.arguments = bundle

                val transaction =
                    fragmentManager?.
                    beginTransaction()?.
                    replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
                transaction?.commit()
                fragmentManager?.beginTransaction()?.commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        return GenreHolder(
            CardviewPlaceholderGenreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: GenreAdapter.GenreHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)
    }
}