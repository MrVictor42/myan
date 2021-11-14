package com.victor.myan.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.victor.myan.R
import com.victor.myan.databinding.PersonalListRecyclerviewBinding
import com.victor.myan.baseFragments.BaseListFragment
import com.victor.myan.model.PersonalList

class PersonalListAdapter : ListAdapter<PersonalList, PersonalListAdapter.PersonalListHolder>(MyDiffUtil) {

    companion object MyDiffUtil : DiffUtil.ItemCallback<PersonalList>() {
        override fun areItemsTheSame(oldItem: PersonalList, newItem: PersonalList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PersonalList, newItem: PersonalList): Boolean {
            return oldItem.ID == newItem.ID
        }
    }

    inner class PersonalListHolder(binding : PersonalListRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.imagePersonalList
        private val name = binding.namePersonalList
        private val description = binding.descriptionPersonalList

        fun bind(personalList: PersonalList) {
            name.text = personalList.name
            description.text = personalList.description

            Glide.with(itemView.context).load(personalList.image).listener(object :
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

            itemView.setOnClickListener {
                val fragment = BaseListFragment()
                val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager

                val bundle = Bundle()
                bundle.putString("ID", personalList.ID)
                bundle.putString("description", personalList.description)
                bundle.putString("image", personalList.image)
                bundle.putString("name", personalList.name)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalListHolder {
        return PersonalListHolder(
            PersonalListRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: PersonalListHolder, position: Int) {
        val personalList = getItem(position)
        holder.bind(personalList)
    }
}