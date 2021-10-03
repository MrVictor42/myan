package com.victor.myan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.victor.myan.databinding.PersonalListAddRemoveBinding
import com.victor.myan.model.Anime
import com.victor.myan.model.PersonalList
import com.victor.myan.viewmodel.PersonalListViewModel

class PersonalListAddRemoveAdapter : ListAdapter<PersonalList, PersonalListAddRemoveAdapter.PersonalListAddRemoveHolder>(MyDiffUtil) {

    private lateinit var animeSelected : Anime
    private val TAG = PersonalListAddRemoveAdapter::class.java.simpleName

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
                if(animeSelected != null) {
                    val personalListViewModel = PersonalListViewModel()
                    val currentList = personalListViewModel.listRef.ref.orderByChild("id").equalTo(personalList.ID)
                    val animeList : MutableList<Anime> = arrayListOf()
                    val animeRef = currentList.ref.child(personalList.ID).child("anime")

                    animeRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()) {
                                for (postSnapshot in snapshot.children) {
                                    animeList.add(postSnapshot.getValue(Anime::class.java)!!)
                                }
                                for(aux in 0 until animeList.size) {
                                    if(animeSelected.malID == animeList[aux].malID) {
//                                        result = "This anime already registered in this list"
                                        Log.e(TAG, "This anime already registered in this list!")
                                    } else {
//                                        result = saveAnime(anime, idList)
                                    }
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }



                /*


            animeRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        Log.i(TAG, "Anime List already exists!")
                        for (postSnapshot in snapshot.children) {
                            animeList.add(postSnapshot.getValue(Anime::class.java)!!)
                        }
                        for(aux in 0 until animeList.size) {
                            if(anime.malID == animeList[aux].malID) {
                                result = "This anime already registered in this list"
                                Log.e(TAG, "This anime already registered in this list!")
                            } else {
                                result = saveAnime(anime, idList)
                            }
                        }
                    } else {
                        Log.i(TAG, "Anime List was created")
                        result = saveAnime(anime, idList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Error in Firebase")
                }
            })
        }

        return result
    }
                 */
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