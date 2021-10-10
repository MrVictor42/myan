package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
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
import com.victor.myan.databinding.CardviewPlaceholderVerticalBinding
import com.victor.myan.model.Actor
import com.victor.myan.baseFragments.BaseActorDetailFragment

class ActorAdapter : ListAdapter<Actor, ActorAdapter.ActorHolder>(ActorAdapter) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.malID == newItem.malID
        }
    }

    inner class ActorHolder(binding: CardviewPlaceholderVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.image
        private val name = binding.name

        fun bind(actor : Actor) {
            Glide.with(itemView.context).load(actor.imageUrl).listener(object :
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
            name.text = actor.name

            itemView.setOnClickListener {
                val fragment = BaseActorDetailFragment()
                val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager

                val bundle = Bundle()
                bundle.putInt("mal_id", actor.malID)

                fragment.arguments = bundle

                val transaction = fragmentManager?.beginTransaction()?.replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
                transaction?.commit()
                fragmentManager?.beginTransaction()?.commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorAdapter.ActorHolder {
        return ActorHolder(
            CardviewPlaceholderVerticalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ActorAdapter.ActorHolder, position: Int) {
        val actor = getItem(position)
        holder.bind(actor)
    }
}