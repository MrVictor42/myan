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
import com.victor.myan.databinding.CardviewPlaceholderBinding
import com.victor.myan.model.Actor
import com.victor.myan.baseFragments.BaseActorDetailFragment
import com.victor.myan.helper.DiffUtilHelper

class ActorAdapter : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    private var actorList = emptyList<Actor>()

    inner class ActorViewHolder(val binding: CardviewPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            CardviewPlaceholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val image = holder.binding.image
        val name = holder.binding.name

        Glide.with(holder.itemView.context).load(actorList[position].imageURL).listener(object :
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?,
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

        name.text = actorList[position].name

        holder.itemView.setOnClickListener {
            val fragment = BaseActorDetailFragment()
            val fragmentManager =
                (holder.itemView.context as FragmentActivity?)?.supportFragmentManager

            val bundle = Bundle()
            bundle.putInt("mal_id", actorList[position].malID)

            fragment.arguments = bundle

            val transaction =
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
            transaction?.commit()
            fragmentManager?.beginTransaction()?.commit()
        }
    }

    override fun getItemCount(): Int {
        return actorList.size
    }

    fun setData(newActorList: List<Actor>) {
        val diffUtil = DiffUtilHelper(actorList, newActorList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        actorList = newActorList
        diffResults.dispatchUpdatesTo(this)
    }
}