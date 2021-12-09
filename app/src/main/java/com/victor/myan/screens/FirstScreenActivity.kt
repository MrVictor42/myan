package com.victor.myan.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseLayout
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Categories
import com.victor.myan.viewmodel.AnimeViewModel
import com.victor.myan.viewmodel.MangaViewModel
import java.util.*
import kotlin.collections.ArrayList

class FirstScreenActivity : AppCompatActivity() {

    private val auxFunctionsHelper = AuxFunctionsHelper()
    private val animeViewModel by lazy {
        ViewModelProvider(this)[AnimeViewModel::class.java]
    }
    private val mangaViewModel by lazy {
        ViewModelProvider(this)[MangaViewModel::class.java]
    }
    private val currentYear = auxFunctionsHelper.getCurrentYear()
    private val currentDay = auxFunctionsHelper.getCurrentDay().lowercase(Locale.getDefault())
    private val currentSeason = auxFunctionsHelper.getSeason().lowercase(Locale.getDefault())
    private val categoryList : MutableList<Categories> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen_activity)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor =  ContextCompat.getColor(this, R.color.black)

        Handler(Looper.getMainLooper()).postDelayed({
            if(auxFunctionsHelper.userHasConnection(this)) {
                if(auxFunctionsHelper.userIsAuthenticated()) {
                    feed()
                } else {
                    val intent = Intent(this, PresentationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this, WithoutConnectionActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)
    }

    private fun feed() {
        animeViewModel.getAnimeListTodayApi(currentDay)
        animeViewModel.animeListToday.observe(this@FirstScreenActivity, { animeToday ->
            when(animeToday) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(animeToday.data != null) {
                        val category = Categories()

                        category.type = "anime"
                        category.title = animeViewModel.currentDayFormatted
                        category.categories.addAll(animeToday.data)

                        categoryList.add(category)
                        feedAnimeAiring()
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }

    private fun feedAnimeAiring() {
        animeViewModel.getAnimeListAiringApi()
        animeViewModel.animeListAiring.observe(this@FirstScreenActivity, { airingAnime ->
            when(airingAnime) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(airingAnime.data != null) {
                        val category = Categories()

                        category.type = "anime"
                        category.title = "Anime Airing"
                        category.categories.addAll(airingAnime.data)

                        categoryList.add(category)
                        feedMangaAiring()
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }

    private fun feedMangaAiring() {
        mangaViewModel.getMangaListAiringApi()
        mangaViewModel.mangaListAiring.observe(this@FirstScreenActivity, { mangaAiring ->
            when(mangaAiring) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(mangaAiring.data != null) {
                        val category = Categories()

                        category.type = "manga"
                        category.title = "Manga Airing"
                        category.categories.addAll(mangaAiring.data)

                        categoryList.add(category)
                        feedAnimeSeason()
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }

    private fun feedAnimeSeason() {
        animeViewModel.getAnimeListSeasonApi(currentYear, currentSeason)
        animeViewModel.animeListSeason.observe(this@FirstScreenActivity, { seasonAnime ->
            when(seasonAnime) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(seasonAnime.data != null) {
                        val category = Categories()

                        category.type = "anime"
                        category.title = animeViewModel.currentSeasonFormatted
                        category.categories.addAll(seasonAnime.data)

                        categoryList.add(category)
                        feedAnimeTop()
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }

    private fun feedAnimeTop() {
        animeViewModel.getAnimeListTopApi()
        animeViewModel.animeListTop.observe(this, { animeTop ->
            when(animeTop) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(animeTop.data != null) {
                        val category = Categories()

                        category.type = "anime"
                        category.title = "Anime Top"
                        category.categories.addAll(animeTop.data)

                        categoryList.add(category)
                        feedMangaTop()
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }

    private fun feedMangaTop() {
        mangaViewModel.getMangaListTopApi()
        mangaViewModel.mangaTopList.observe(this, { mangaTop ->
            when(mangaTop) {
                is ScreenStateHelper.Loading -> {

                }
                is ScreenStateHelper.Success -> {
                    if(mangaTop.data != null) {
                        val category = Categories()

                        category.type = "manga"
                        category.title = "Manga Top"
                        category.categories.addAll(mangaTop.data)

                        categoryList.add(category)
                        val intent = Intent(this, BaseLayout::class.java)
                        intent.putParcelableArrayListExtra("categoryList", ArrayList(categoryList))
                        startActivity(intent)
                        finish()
                    }
                }
                is ScreenStateHelper.Error -> {

                }
                else -> {

                }
            }
        })
    }
}