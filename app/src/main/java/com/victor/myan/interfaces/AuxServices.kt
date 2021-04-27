package com.victor.myan.interfaces

import android.view.View

interface AuxServices {
    fun validateFields(userName: String, email: String, password: String) : String
    fun capitalize(str: String) : String
    fun message(view: View, messageResult: String, indefinite: Boolean)
}