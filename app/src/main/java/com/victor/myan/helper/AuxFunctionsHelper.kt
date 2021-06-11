package com.victor.myan.helper

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.victor.myan.enums.DaysEnum
import com.victor.myan.enums.MonthsEnum
import com.victor.myan.enums.SeasonsEnum
import java.util.*
import java.util.regex.Pattern

class AuxFunctionsHelper {

    fun validateFields(email: String, password: String): String {
        return when {
            email.isEmpty() -> capitalize("fill the field email!")
            password.isEmpty() -> capitalize("fill the field password!")
            email.isEmpty() && password.isEmpty() -> capitalize("fill every fields!")
            else -> ""
        }
    }

    fun capitalize(str: String): String {
        val words = str.split(" ").toMutableList()
        var output = ""

        for(word in words) {
            output += word.capitalize(Locale.ENGLISH) + " "
        }
        output = output.trim()
        return output
    }

    fun message(view: View, messageResult: String) {
        val snackbar = Snackbar.make(view, capitalize(messageResult), Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
        snackbar.show()
    }

    fun getCurrentDay(): String {
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

    fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun getSeason(): String {
        val date = Date()
        val calendar = GregorianCalendar()
        calendar.time = date
        var currentMonth = calendar.get(Calendar.MONTH)
        currentMonth += 1

        return when {
            currentMonth >= MonthsEnum.March.month && currentMonth <= MonthsEnum.May.month ->
                SeasonsEnum.Spring.season
            currentMonth > MonthsEnum.May.month && currentMonth <= MonthsEnum.August.month ->
                SeasonsEnum.Summer.season
            currentMonth > MonthsEnum.August.month && currentMonth <= MonthsEnum.November.month ->
                SeasonsEnum.Fall.season
            else -> SeasonsEnum.Winter.season
        }
    }
}