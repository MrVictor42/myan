package com.victor.myan.services.interfaces

import com.victor.myan.model.User

interface UserServices {
    fun save(user: User)
    fun edit(user: User)
    fun delete(user: User)
}