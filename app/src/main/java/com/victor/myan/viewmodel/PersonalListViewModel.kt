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
import com.victor.myan.model.PersonalList

class PersonalListViewModel : ViewModel() {

    val personalList : MutableLiveData<ScreenStateHelper<List<PersonalList>?>> = MutableLiveData()

    private val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
    private val TAG = PersonalListViewModel::class.java.simpleName
    private val userRef = FirebaseDatabase.getInstance().getReference("users").orderByChild("userID").equalTo(currentUser)
    val listRef = userRef.ref.child(currentUser).child("list").orderByChild("userID").equalTo(currentUser)

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
}