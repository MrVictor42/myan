package com.victor.myan.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.auth.FirebaseAuth
import com.victor.myan.enums.AnimeTypeEnum
import com.victor.myan.enums.DaysEnum
import com.victor.myan.enums.MonthsEnum
import com.victor.myan.enums.SeasonsEnum
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale
import java.util.Date

class AuxFunctionsHelper {

    fun validateField(field : String, editText: AppCompatEditText): Boolean {
        return when {
            field.isEmpty() -> {
                editText.error = capitalize("fill the field ${editText.hint}")
                editText.requestFocus()
                false
            }
            field.length < 3 -> {
                editText.error = capitalize(
                    "please, insert the field ${editText.hint} with more 3 characters"
                )
                editText.requestFocus()
                false
            }
            else -> {
                editText.clearFocus()
                true
            }
        }
    }

    fun capitalize(str: String): String {
        val words = str.split(" ").toMutableList()
        var output = ""

        for(word in words) {
            output += word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() } + " "
        }
        output = output.trim()
        return output
    }

    fun getCurrentDay() : String {
        return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            1 -> DaysEnum.Sunday.name
            2 -> DaysEnum.Monday.name
            3 -> DaysEnum.Tuesday.name
            4 -> DaysEnum.Wednesday.name
            5 -> DaysEnum.Thursday.name
            6 -> DaysEnum.Friday.name
            else -> DaysEnum.Saturday.name
        }
    }

    fun getCurrentDays() : String {
        return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            1 -> DaysEnum.Sundays.name
            2 -> DaysEnum.Mondays.name
            3 -> DaysEnum.Tuesdays.name
            4 -> DaysEnum.Wednesdays.name
            5 -> DaysEnum.Thursdays.name
            6 -> DaysEnum.Fridays.name
            else -> DaysEnum.Saturdays.name
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
                SeasonsEnum.Spring.name
            currentMonth > MonthsEnum.May.month && currentMonth <= MonthsEnum.August.month ->
                SeasonsEnum.Summer.name
            currentMonth > MonthsEnum.August.month && currentMonth <= MonthsEnum.November.month ->
                SeasonsEnum.Fall.name
            else -> SeasonsEnum.Winter.name
        }
    }

    fun userHasConnection(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun userIsAuthenticated(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    fun formatPremiered(premiered : String) : String {
        var year = ""

        when {
            premiered.contains(SeasonsEnum.Spring.name,true) -> {
                year = premiered.substring(6,11)
            }
            premiered.contains(SeasonsEnum.Winter.name,true) -> {
                year = premiered.substring(6,11)
            }
            premiered.contains(SeasonsEnum.Fall.name,true) -> {
                year = premiered.substring(5,9)
            }
            premiered.contains(SeasonsEnum.Summer.name,true) -> {
                year = premiered.substring(6,11)
            }
        }

        return year
    }

    fun formatDurationEpisode(typeAnime : String, duration : String) : String {
        var episodePerMinute = ""

        when(typeAnime) {
            AnimeTypeEnum.TV.name, AnimeTypeEnum.OVA.name -> {
                episodePerMinute = when(duration.length) {
                    13 -> duration.substring(0,6)
                    else -> duration
                }
            }

            AnimeTypeEnum.Special.name, AnimeTypeEnum.ONA.name -> {
                episodePerMinute = when(duration.length) {
                    12 -> duration.substring(0,6)
                    13 -> duration.substring(0,6)
                    else -> duration
                }
            }

            AnimeTypeEnum.Movie.name, AnimeTypeEnum.Music.name -> episodePerMinute = duration
        }

        return episodePerMinute
    }
}