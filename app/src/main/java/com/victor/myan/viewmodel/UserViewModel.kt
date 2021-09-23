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
import com.victor.myan.model.User

class UserViewModel : ViewModel() {

    val currentUser : MutableLiveData<ScreenStateHelper<User>?> = MutableLiveData()

    private val TAG = UserViewModel::class.java.simpleName

    fun getCurrentUser() {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef = FirebaseDatabase.getInstance().getReference("users")

        userRef.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.i(TAG, "Found user!!")
                    currentUser.postValue(ScreenStateHelper.Success(dataSnapshot.getValue(User::class.java)))
                } else {
                    Log.e(TAG, "User not found...")
                    currentUser.postValue(ScreenStateHelper.Empty("User not found...", null))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Something Wrong Happened!")
            }
        })
    }
}