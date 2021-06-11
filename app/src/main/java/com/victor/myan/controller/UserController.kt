package com.victor.myan.controller

import android.view.View
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.model.User

class UserController {

    private val auxServicesHelper = AuxFunctionsHelper()
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    fun create(user: User, view: View) {

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("users")
        mAuth = FirebaseAuth.getInstance()

        mAuth!!.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if(it.isSuccessful) {
                auxServicesHelper.message(view, "the user was successfully registered!")
            } else {
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            when(it) {
                is FirebaseAuthWeakPasswordException ->
                    auxServicesHelper.message(view,"insert a password with 6 no minimum characters!")
                is FirebaseAuthUserCollisionException ->
                    auxServicesHelper.message(view,"this account already exists!")
                is FirebaseNetworkException ->
                    auxServicesHelper.message(view,"without connection with internet!")
                else -> auxServicesHelper.message(view,"${it.message}!")
            }
            return@addOnFailureListener
        }
    }

    fun userIsAuthenticated(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}