package com.victor.myan.interfaces

import android.view.View
import com.victor.myan.model.User

interface UserServices {
    fun create(user: User, view: View) : Boolean
    fun edit(user: User): Boolean
    fun delete(user: User) : Boolean
    fun getUser(user: User) : User
}