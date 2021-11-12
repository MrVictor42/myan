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
import com.victor.myan.baseFragments.BaseMangaDetailFragment
import com.victor.myan.databinding.CardviewPlaceholderHorizontalBinding
import com.victor.myan.helper.DiffUtilHelper
import com.victor.myan.model.Manga

class MangaHorizontalAdapter : RecyclerView.Adapter<MangaHorizontalAdapter.MangaViewHolder>() {

    private var mangaList = emptyList<Manga>()

    inner class MangaViewHolder(val binding : CardviewPlaceholderHorizontalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaHorizontalAdapter.MangaViewHolder {
        return MangaViewHolder(CardviewPlaceholderHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val image = holder.binding.image

        Glide.with(holder.itemView.context).load(mangaList[position].imageURL).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }).into(image)

        holder.itemView.setOnClickListener {
            val fragment = BaseMangaDetailFragment()
            val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

            val bundle = Bundle()
            bundle.putInt("mal_id", mangaList[position].malID)

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
        return mangaList.size
    }

    fun setData(newMangaList : List<Manga>) {
        val diffUtil = DiffUtilHelper(mangaList, newMangaList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        mangaList = newMangaList
        diffResults.dispatchUpdatesTo(this)
    }
}