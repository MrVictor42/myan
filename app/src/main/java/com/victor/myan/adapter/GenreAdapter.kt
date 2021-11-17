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
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseGenreFragment
import com.victor.myan.databinding.CardviewPlaceholderGenreBinding
import com.victor.myan.helper.DiffUtilHelper
import com.victor.myan.model.Genre

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private var genreList = emptyList<Genre>()

    inner class GenreViewHolder(val binding : CardviewPlaceholderGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(CardviewPlaceholderGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val image = holder.binding.image
        val title = holder.binding.title

        Glide.with(holder.itemView.context).load(genreList[position].imageURL).listener(object :
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
                return false
            }
        }).into(image)
        title.text = genreList[position].name

        holder.itemView.setOnClickListener {
            val fragment = BaseGenreFragment()
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

    fun setData(newGenreList : List<Genre>) {
        val diffUtil = DiffUtilHelper(genreList, newGenreList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        genreList = newGenreList
        diffResults.dispatchUpdatesTo(this)
    }
}