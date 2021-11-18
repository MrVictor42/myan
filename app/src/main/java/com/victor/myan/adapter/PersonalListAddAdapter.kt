package com.victor.myan.adapter

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.victor.myan.R
import com.victor.myan.databinding.PersonalListAddRemoveBinding
import com.victor.myan.model.Anime
import com.victor.myan.fragments.dialogs.ListDialogFragment
import com.victor.myan.model.Manga
import com.victor.myan.model.PersonalList
import com.victor.myan.viewmodel.PersonalListViewModel

class PersonalListAddAdapter(private val dialogFragment : ListDialogFragment) : ListAdapter<PersonalList, PersonalListAddAdapter.PersonalListAddRemoveHolder>(MyDiffUtil) {

    private val mangaList : MutableList<Manga> = arrayListOf()
    private val animeList : MutableList<Anime> = arrayListOf()
    private val TAG : String = PersonalListAddAdapter::class.java.simpleName

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

        fun bind(personalList: PersonalList, animeList: MutableList<Anime>, mangaList: MutableList<Manga>) {
            name.text = personalList.name

            itemView.setOnClickListener {
                when {
                    animeList.size > 0 -> {
                        checkAndSave("anime", personalList, itemView.context)
                    }
                    mangaList.size > 0 -> {
                        checkAndSave("manga", personalList, itemView.context)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalListAddRemoveHolder {
        return PersonalListAddRemoveHolder(
            PersonalListAddRemoveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: PersonalListAddRemoveHolder, position: Int) {
        val personalList = getItem(position)
        holder.bind(personalList, animeList, mangaList)
    }

    fun setData(manga: Manga?, anime: Anime?) {
        if(anime != null) {
            animeList.add(anime)
        }
        if(manga != null) {
            mangaList.add(manga)
        }
    }

    private fun showDialog(context : Context, layout : Int, message : String) {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        val dialogButton = dialog.findViewById<AppCompatButton>(R.id.btn_dialog)
        val dialogMessage = dialog.findViewById<TextView>(R.id.text_view_dialog)

        dialog.show()
        dialogMessage.text = message
        dialogButton.setOnClickListener {
            dialog.dismiss()
            dialogFragment.dismiss()
        }
    }

    private fun checkAndSave(type: String, personalList: PersonalList, context: Context) {
        val personalListViewModel = PersonalListViewModel()
        val currentList = personalListViewModel.listRef.ref.orderByChild("id").equalTo(personalList.ID)
        val animeRef = currentList.ref.child(personalList.ID).child("anime")
        val mangaRef = currentList.ref.child(personalList.ID).child("manga")
        val animeListRef : MutableList<Anime> = arrayListOf()
        val mangaListRef : MutableList<Manga> = arrayListOf()

        when(type) {
            "anime" -> {
                animeRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        if (result != null) {
                            if (result.exists()) {
                                result.let {
                                    var exists = false
                                    result.children.map { snapshot ->
                                        animeListRef.add(snapshot.getValue(Anime::class.java)!!)
                                    }
                                    animeListRef.forEach { anime ->
                                        if(anime.malID == animeList[0].malID) {
                                            showDialog(
                                                context,
                                                R.layout.custom_warming_dialog,
                                                "The anime ${ animeList[0].title } already on this list"
                                            )
                                            exists = true
                                        } else {
                                            Log.i(TAG, "The anime is not on the list yet")
                                        }
                                    }
                                    if(exists) {
                                        Log.i(TAG, "The anime already on this list")
                                        animeList.clear()
                                    } else {
                                        saveAnime(animeList[0], animeRef, context, personalList.name)
                                    }
                                }
                            } else {
                                saveAnime(animeList[0], animeRef, context, personalList.name)
                            }
                        }
                    } else {
                        Log.e(TAG, "Error on Firebase")
                    }
                }.addOnFailureListener {
                    Log.e(TAG, "Error on Firebase")
                }
            }
            "manga" -> {
                mangaRef.get().addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        val result = task.result
                        if(result != null) {
                            if(result.exists()) {
                                result.let {
                                    var exists = false
                                    result.children.map { snapshot ->
                                        mangaListRef.add(snapshot.getValue(Manga::class.java)!!)
                                    }
                                    mangaListRef.forEach { manga ->
                                        if(manga.malID == mangaList[0].malID) {
                                            showDialog(
                                                context,
                                                R.layout.custom_warming_dialog,
                                                "The manga ${ mangaList[0].title } already on this list"
                                            )
                                            exists = true
                                        } else {
                                            Log.i(TAG, "The manga is not on the list yet")
                                        }
                                    }
                                    if(exists) {
                                        Log.i(TAG, "The manga already on this list")
                                        mangaList.clear()
                                    } else {
                                        saveManga(mangaList[0], mangaRef, context, personalList.name)
                                    }
                                }
                            } else {
                                saveManga(mangaList[0], mangaRef, context, personalList.name)
                            }
                        }
                    } else {
                        Log.e(TAG, "Error on Firebase")
                    }
                }.addOnFailureListener {
                    Log.e(TAG, "Error on Firebase")
                }
            }
        }
    }

    private fun saveManga(manga: Manga, mangaRef: DatabaseReference, context: Context, nameList: String) {
        mangaRef.child(manga.malID.toString()).setValue(manga).addOnSuccessListener {
            showDialog(
                context,
                R.layout.custom_positive_dialog,
                "The manga ${ manga.title }, was inserted in List " +
                        "$nameList with success!!"
            )
            Log.i(TAG, "Manga inserted with success!!")
            mangaList.clear()
        }.addOnFailureListener {
            showDialog(
                context,
                R.layout.custom_negative_dialog,
                "Something was wrong... try later!"
            )
            Log.e(TAG, "Manga doesn't inserted with success!!")
            mangaList.clear()
        }
    }

    private fun saveAnime(anime: Anime, animeRef: DatabaseReference, context: Context, nameList: String)  {
        animeRef.child(anime.malID.toString()).setValue(anime).addOnSuccessListener {
            showDialog(
                context,
                R.layout.custom_positive_dialog,
                "The anime ${ anime.title }, was inserted in List " +
                        "$nameList with success!!"
            )
            Log.i(TAG, "Anime inserted with success!!")
            animeList.clear()
        }.addOnFailureListener {
            showDialog(
                context,
                R.layout.custom_negative_dialog,
                "Something was wrong... try later!"
            )
            Log.e(TAG, "Anime doesn't inserted with success!!")
            animeList.clear()
        }
    }
}