package com.victor.myan.services.impl

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.enums.DaysEnum
import com.victor.myan.enums.MonthsEnum
import com.victor.myan.enums.SeasonsEnum
import com.victor.myan.services.interfaces.AuxServices
import java.util.*

class AuxServicesImpl : AuxServices {
    override fun validateFields(email: String, password: String): String {
        return when {
            email.isEmpty() -> capitalize("fill the field email!")
            password.isEmpty() -> capitalize("fill the field password!")
            email.isEmpty() && password.isEmpty() -> capitalize("fill every fields!")
            else -> ""
        }
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

    override fun getCurrentDay(): String {
        return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            1 -> DaysEnum.Sunday.day
            2 -> DaysEnum.Monday.day
            3 -> DaysEnum.Tuesday.day
            4 -> DaysEnum.Wednesday.day
            5 -> DaysEnum.Thursday.day
            6 -> DaysEnum.Friday.day
            else -> DaysEnum.Saturday.day
        }
    }

    override fun getSeason(): String {
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH + 1)

        return when {
            currentMonth >= MonthsEnum.March.month && currentMonth <= MonthsEnum.May.month ->
                SeasonsEnum.Spring.season
            currentMonth >= MonthsEnum.May.month && currentMonth <= MonthsEnum.August.month ->
                SeasonsEnum.Summer.season
            currentMonth > MonthsEnum.August.month && currentMonth <= MonthsEnum.November.month ->
                SeasonsEnum.Fall.season
            else -> SeasonsEnum.Winter.season
        }
    }
}