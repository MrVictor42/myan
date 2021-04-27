package com.victor.myan.services

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.interfaces.AuxServices

class AuxServicesImpl : AuxServices {
    override fun validateFields(userName: String, email: String, password: String): String {
        var messageError: String = ""

        messageError = when {
            userName.isEmpty() -> capitalize("fill the field username!")
            email.isEmpty() -> capitalize("fill the field email!")
            password.isEmpty() -> capitalize("fill the field password!")
            userName.isEmpty() && email.isEmpty() && password.isEmpty() -> capitalize("fill every fields!")
            else -> ""
        }
        return messageError
    }

    override fun capitalize(str: String): String {
        val words = str.split(" ").toMutableList()
        var output: String = ""

        for(word in words) {
            output += word.capitalize() + " "
        }
        output = output.trim()
        return output
    }

    override fun message(view: View, messageResult: String, indefinite: Boolean) {
        if(!indefinite) {
            val snackbar = Snackbar.make(view, capitalize(messageResult), Snackbar.LENGTH_LONG)
            snackbar.setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
            snackbar.show()
        } else {
            val snarckbar = Snackbar.make(view, capitalize(messageResult), Snackbar.LENGTH_INDEFINITE)
            snarckbar.setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {

            })
            snarckbar.show()
        }
    }
}