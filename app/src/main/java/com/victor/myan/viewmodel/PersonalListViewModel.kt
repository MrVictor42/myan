package com.victor.myan.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.Manga
import com.victor.myan.model.PersonalList

class PersonalListViewModel : ViewModel() {

    val personalList : MutableLiveData<ScreenStateHelper<List<PersonalList>?>> = MutableLiveData()

    private val listID : MutableLiveData<String> = MutableLiveData()
    private val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
    private val listRef =
        FirebaseDatabase
        .getInstance().getReference("list")
        .orderByChild("userID").equalTo(currentUser)
    private val animeList =
        FirebaseDatabase
            .getInstance()
            .getReference("list")
            .child("anime")
            .orderByChild("userID").equalTo(currentUser)

    private val TAG = PersonalListViewModel::class.java.simpleName

    fun getPersonalList() {
        val personalListFinal : MutableList<PersonalList> = arrayListOf()

        personalList.postValue(ScreenStateHelper.Loading(null))
        listRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        personalListFinal.add(postSnapshot.getValue(PersonalList::class.java)!!)
                    }
                    personalList.postValue(ScreenStateHelper.Success(personalListFinal))
                } else {
                    personalList.postValue(ScreenStateHelper.Empty("Does not exists this list yet", null))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Not found the list")
            }
        })
    }

    fun existsList() : Boolean {
        var valid = false
        listRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                valid = snapshot.exists()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Not found the list")
            }
        })
        return valid
    }

    fun addAnimeManga(anime: Anime?, manga: Manga?) {
        if(anime != null) {
//            existsInList(anime, manga)
        }
        if(manga != null) {
//            existsInList(anime, manga)
        }
    }

    private fun existsInList(anime: Anime?, manga: Manga?) {
        if(anime != null) {
            val animeListResult : MutableList<PersonalList> = arrayListOf()
            animeList.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapshot in snapshot.children) {
                        animeListResult.add(postSnapshot.getValue(PersonalList::class.java)!!)
                    }
                    for (aux in 0 until animeListResult.size) {
                        if(animeListResult.size == 0) {
//                            FirebaseDatabase.getInstance().getReference("anime").child();
//                            listRef.child(personalList.ID).setValue(personalList).addOnSuccessListener {
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}