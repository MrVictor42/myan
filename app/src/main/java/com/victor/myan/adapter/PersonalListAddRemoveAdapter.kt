package com.victor.myan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.databinding.PersonalListAddRemoveBinding
import com.victor.myan.model.Anime
import com.victor.myan.model.PersonalList
import com.victor.myan.viewmodel.PersonalListViewModel

class PersonalListAddRemoveAdapter : ListAdapter<PersonalList, PersonalListAddRemoveAdapter.PersonalListAddRemoveHolder>(MyDiffUtil) {

    private lateinit var animeSelected : Anime

    companion object MyDiffUtil : DiffUtil.ItemCallback<PersonalList>() {
        override fun areItemsTheSame(oldItem: PersonalList, newItem: PersonalList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PersonalList, newItem: PersonalList): Boolean {
            return oldItem.ID == newItem.ID
        }
    }

    inner class PersonalListAddRemoveHolder(binding : PersonalListAddRemoveBinding) : RecyclerView.ViewHolder(binding.root) {
        private val name = binding.namePersonalList

        fun bind(personalList: PersonalList, animeSelected: Anime?) {
            name.text = personalList.name

            itemView.setOnClickListener {
                val personalListViewModel = PersonalListViewModel()
                if(!personalListViewModel.existsInList(animeSelected, personalList.ID)) {
                    Toast.makeText(
                        itemView.context,
                        "Item inserted in list ${personalList.name} with success!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        itemView.context,
                        "Item doesn't inserted in list ${personalList.name} with success!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalListAddRemoveAdapter.PersonalListAddRemoveHolder {
        return PersonalListAddRemoveHolder(
            PersonalListAddRemoveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: PersonalListAddRemoveAdapter.PersonalListAddRemoveHolder, position: Int) {
        val personalList = getItem(position)
        holder.bind(personalList, animeSelected)
    }

    fun addAnime(anime: Anime) : Anime {
        animeSelected = anime
        return animeSelected
    }
}