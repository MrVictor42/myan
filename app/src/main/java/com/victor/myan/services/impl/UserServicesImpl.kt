package com.victor.myan.services.impl

import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.model.User
import com.victor.myan.services.interfaces.UserServices

class UserServicesImpl : UserServices {

    override fun save(user: User) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener {
            if(it.isSuccessful) {

            } else {

            }
        }.addOnFailureListener {

        }
    }

    override fun edit(user: User) {
        TODO("Not yet implemented")
    }

    override fun delete(user: User) {
        TODO("Not yet implemented")
    }
}