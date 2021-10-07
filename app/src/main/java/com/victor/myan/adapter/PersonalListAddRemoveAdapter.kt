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
import com.victor.myan.model.PersonalList
import com.victor.myan.viewmodel.PersonalListViewModel
import com.victor.myan.fragments.dialogs.ListDialogFragment

class PersonalListAddRemoveAdapter(private val dialogFragment : ListDialogFragment) : ListAdapter<PersonalList, PersonalListAddRemoveAdapter.PersonalListAddRemoveHolder>(MyDiffUtil) {
    private lateinit var animeSelected : Anime
    private val TAG : String = PersonalListAddRemoveAdapter::class.java.simpleName

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

                    animeRef.get().addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            val result = task.result
                            if (result != null) {
                                if(result.exists()) {
                                    result.let {
                                        var exists = false
                                        result.children.map { snapshot ->
                                            animeList.add(snapshot.getValue(Anime::class.java)!!)
                                        }
                                        animeList.forEach { anime ->
                                            if(anime.malID == animeSelected.malID) {
                                                showDialog(
                                                    itemView.context,
                                                    R.layout.custom_warming_dialog,
                                                    "The anime ${animeSelected.title} already on this list"
                                                )
                                                exists = true
                                            } else {
                                                Log.i(TAG, "The anime is not on the list yet")
                                            }
                                        }
                                        if(exists) {
                                            Log.i(TAG, "The anime already on this list")
                                        } else {
                                            saveAnime(animeSelected, animeRef, itemView.context, personalList.name)
                                        }
                                    }
                                } else {
                                    saveAnime(animeSelected, animeRef, itemView.context, personalList.name)
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

    private fun saveAnime(anime: Anime, animeRef: DatabaseReference, context: Context, nameList: String)  {
        animeRef.child(anime.malID.toString()).setValue(anime).addOnSuccessListener {
            showDialog(
                context,
                R.layout.custom_positive_dialog,
                "The anime ${anime.title}, was inserted in List " +
                        "$nameList with success!!"
            )
            Log.i(TAG, "Anime inserted with success!!")
        }.addOnFailureListener {
            showDialog(
                context,
                R.layout.custom_negative_dialog,
                "Something was wrong... try later!"
            )
            Log.e(TAG, "Anime doesn't inserted with success!!")
        }
    }
}