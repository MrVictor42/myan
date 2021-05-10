package com.victor.myan.services.impl

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.services.interfaces.AuxServices
import java.util.*

class AuxServicesImpl : AuxServices {
    override fun validateFields(email: String, password: String): String {
        var messageError: String = ""

        messageError = when {
            email.isEmpty() -> capitalize("fill the field email!")
            password.isEmpty() -> capitalize("fill the field password!")
            email.isEmpty() && password.isEmpty() -> capitalize("fill every fields!")
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

    override fun message(view: View, messageResult: String) {
        val snackbar = Snackbar.make(view, capitalize(messageResult), Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
        snackbar.show()
    }

    override fun getCurrentMonth(): Int {
        return 0
    }

    override fun getCurrentDay(): Int {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    }
}