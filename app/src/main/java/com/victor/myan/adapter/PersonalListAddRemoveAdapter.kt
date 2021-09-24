package com.victor.myan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victor.myan.databinding.PersonalListAddRemoveBinding
import com.victor.myan.model.Anime
import com.victor.myan.model.Manga
import com.victor.myan.model.PersonalList
import com.victor.myan.viewmodel.PersonalListViewModel

class PersonalListAddRemoveAdapter : ListAdapter<PersonalList, PersonalListAddRemoveAdapter.PersonalListAddRemoveHolder>(MyDiffUtil) {

    private lateinit var animeSelected : Anime
    private lateinit var mangaSelected : Manga

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

        fun bind(personalList: PersonalList, animeSelected: Anime?, mangaSelected : Manga?) {
            name.text = personalList.name

            itemView.setOnClickListener {
                val personalListViewModel = PersonalListViewModel()

//                val fragment = BaseListDetailFragment()
//                val fragmentManager = (itemView.context as FragmentActivity?)?.supportFragmentManager
//
//                val bundle = Bundle()
//                bundle.putString("ID", personalList.ID)
//                bundle.putString("description", personalList.description)
//                bundle.putString("image", personalList.image)
//                bundle.putString("name", personalList.name)
//
//                fragment.arguments = bundle
//
//                val transaction =
//                    fragmentManager?.
//                    beginTransaction()?.
//                    replace(R.id.fragment_layout, fragment, fragment.javaClass.simpleName)
//                transaction?.commit()
//                fragmentManager?.beginTransaction()?.commit()
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
        holder.bind(personalList, animeSelected, mangaSelected)
    }

    fun addAnime(anime: Anime) : Anime {
        animeSelected = anime
        return animeSelected
    }

    fun addManga(manga: Manga) : Manga {
        mangaSelected = manga
        return mangaSelected
    }
}