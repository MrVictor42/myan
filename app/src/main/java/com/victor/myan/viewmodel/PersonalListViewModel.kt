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

    private val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
    private val userRef = FirebaseDatabase.getInstance().getReference("users").orderByChild("userID").equalTo(currentUser)
    val listRef = userRef.ref.child(currentUser).child("list").orderByChild("userID").equalTo(currentUser)
    private val TAG = PersonalListViewModel::class.java.simpleName

    fun getPersonalList() {
        personalList.postValue(ScreenStateHelper.Loading(null))
        listRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val personalListFinal : MutableList<PersonalList> = arrayListOf()
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

    fun saveAnime(anime: Anime?, idList: String) : String {
        var result = ""
        val currentList = listRef.ref.orderByChild("id").equalTo(idList)
        val animeRef = currentList.ref.child(idList).child("anime").child(listRef.ref.push().key!!)

        animeRef.setValue(anime).addOnSuccessListener {
            Log.i(TAG, "Anime inserted with success!!")
            result = "Anime inserted with success!!"
        }.addOnFailureListener {
            Log.e(TAG, "Anime doesn't inserted with success!!")
            result = "Anime doesn't inserted with success!!"
        }
        return result
    }

    fun check(anime: Anime?, idList: String) : String {
        val mangaList : MutableList<Manga> = arrayListOf()
        val currentList = listRef.ref.orderByChild("id").equalTo(idList)
        var result = ""

        if(anime != null) {
            val animeList : MutableList<Anime> = arrayListOf()
            val animeRef = currentList.ref.child(idList).child("anime")

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
}