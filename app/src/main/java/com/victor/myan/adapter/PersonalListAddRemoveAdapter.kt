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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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

                    animeRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()) {
                                for (postSnapshot in snapshot.children) {
                                    animeList.add(postSnapshot.getValue(Anime::class.java)!!)
                                }
                                for(aux in 0 until animeList.size) {
                                    if(animeSelected.malID == animeList[aux].malID) {

                                    } else {

                                    }
                                }
                            } else {
                                showDialog(
                                    itemView.context,
                                    R.layout.custom_positive_dialog,
                                    "The anime ${animeSelected.title}, was inserted in List " +
                                            "${personalList.name} with success!!"
                                )
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e(TAG, "Error in Firebase")
                        }
                    })
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

    private fun saveAnime(anime: Anime?, idList: String) : String {
        val personalListViewModel = PersonalListViewModel()
        val currentList = personalListViewModel.listRef.ref

        return ""
//        var result = ""
//        val currentList = listRef.ref.orderByChild("id").equalTo(idList)
//        val animeRef = currentList.ref.child(idList).child("anime").child(listRef.ref.push().key!!)
//
//        animeRef.setValue(anime).addOnSuccessListener {
//            Log.i(TAG, "Anime inserted with success!!")
//            result = "Anime inserted with success!!"
//        }.addOnFailureListener {
//            Log.e(TAG, "Anime doesn't inserted with success!!")
//            result = "Anime doesn't inserted with success!!"
//        }
//        return result
    }
}