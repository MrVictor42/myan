package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseAnimeFragment
import com.victor.myan.baseFragments.BaseMangaFragment
import com.victor.myan.databinding.CardviewItemHomeBinding
import com.victor.myan.model.Jikan

class RecyclerViewHorizontalAdapter(
    private val jikanList : List<Jikan>, private val type: String
) : RecyclerView.Adapter<RecyclerViewHorizontalAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(val binding : CardviewItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(CardviewItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return jikanList.size
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val itemImage = holder.binding.image

        Glide.with(holder.itemView.context).load(jikanList[position].imageURL).listener(object :
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
        }).into(itemImage)

        holder.itemView.setOnClickListener {
            if(type == "manga") {
                val fragment = BaseMangaFragment()
                val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

                val bundle = Bundle()
                bundle.putInt("mal_id", jikanList[position].malID)

                fragment.arguments = bundle

                val transaction =
                    fragmentManager?.
                    beginTransaction()?.
                    replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
                transaction?.commit()
                fragmentManager?.beginTransaction()?.commit()
            } else {
                val fragment = BaseAnimeFragment()
                val fragmentManager = (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

                val bundle = Bundle()
                bundle.putInt("mal_id", jikanList[position].malID)

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
}