package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.databinding.PersonalListRecyclerviewBinding
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

//            Glide.with(itemView.context).load(personalList.image).listener(object :
//                RequestListener<Drawable> {
//                override fun onLoadFailed(e: GlideException?, model: Any?,
//                                          target: com.bumptech.glide.request.target.Target<Drawable>?,
//                                          isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: com.bumptech.glide.request.target.Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//            }).into(image)

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, personalList.name, Toast.LENGTH_SHORT).show()
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