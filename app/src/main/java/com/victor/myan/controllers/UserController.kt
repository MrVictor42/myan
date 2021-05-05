package com.victor.myan.controllers

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
                auxServicesImpl.message(view, "the user was successfully registered!")
            } else {
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            when(it) {
                is FirebaseAuthWeakPasswordException ->
                    auxServicesImpl.message(view,"insert a password with 6 no minimum characters!")
                is FirebaseAuthUserCollisionException ->
                    auxServicesImpl.message(view,"this account already exists!")
                is FirebaseNetworkException ->
                    auxServicesImpl.message(view,"without connection with internet!")
                else -> auxServicesImpl.message(view,"${it.message}!")
            }
            return@addOnFailureListener
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

    override fun userIsAuthenticated(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}