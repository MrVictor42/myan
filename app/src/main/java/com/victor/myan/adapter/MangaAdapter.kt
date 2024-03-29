package com.victor.myan.adapter

import android.graphics.drawable.Drawable
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
import com.victor.myan.baseFragments.BaseMangaFragment
import com.victor.myan.databinding.CardviewPlaceholderBinding
import com.victor.myan.helper.DiffUtilHelper
import com.victor.myan.model.Manga

class MangaAdapter : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    private var mangaList = emptyList<Manga>()

    inner class MangaViewHolder(val binding : CardviewPlaceholderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaAdapter.MangaViewHolder {
        return MangaViewHolder(CardviewPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val image = holder.binding.image
        holder.binding.name.visibility = View.GONE

        Glide.with(holder.itemView.context).load(mangaList[position].imageURL).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }).into(image)

        holder.itemView.setOnClickListener {
            val fragment = BaseMangaFragment(mangaList[position].malID)
            val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

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