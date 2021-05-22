package com.victor.myan.services.interfaces

import android.view.View

interface AuxServices {
    fun validateFields(email: String, password: String) : String
    fun capitalize(str: String) : String
    fun message(view: View, messageResult: String)
    fun getCurrentDay() : Int
}