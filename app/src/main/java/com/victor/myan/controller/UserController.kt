package com.victor.myan.controller

import android.view.View
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.victor.myan.services.AuxServicesImpl
import com.victor.myan.interfaces.UserServices
import com.victor.myan.model.User

class UserController : UserServices {

    private val auxServicesImpl = AuxServicesImpl()
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun create(user: User, view: View): Unit {

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("users")
        mAuth = FirebaseAuth.getInstance()

        mAuth!!.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if(it.isSuccessful) {
                val userID = mAuth!!.currentUser!!.uid
                val currentUserDb = mDatabaseReference!!.child(userID)
                currentUserDb.child("name").setValue(user.name)
                auxServicesImpl.message(view, "the user ${user.name} was successfully registered!", false)
            }
        }.addOnFailureListener {
            when(it) {
                is FirebaseAuthWeakPasswordException ->
                    auxServicesImpl.message(view,"insert a password with 6 no minimum characters!", true)
                is FirebaseAuthUserCollisionException ->
                    auxServicesImpl.message(view,"this account already exists!", true)
                is FirebaseNetworkException ->
                    auxServicesImpl.message(view,"without connection with internet!", true)
                else -> auxServicesImpl.message(view,"error registered user!", true)
            }
        }
    }

    override fun edit(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUser(user: User): User {
        TODO("Not yet implemented")
    }
}