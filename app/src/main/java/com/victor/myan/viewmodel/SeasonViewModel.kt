package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class SeasonViewModel : ViewModel() {

    private val auxFunctionsHelper = AuxFunctionsHelper()
    private val currentSeason = auxFunctionsHelper.getSeason()
    private val currentYear = auxFunctionsHelper.getCurrentYear()
    private val _animeListSeason = MutableLiveData<ScreenStateHelper<List<Anime>?>>()
    val animeListSeason : LiveData<ScreenStateHelper<List<Anime>?>>
        get() = _animeListSeason
    val currentSeasonFormatted = auxFunctionsHelper.capitalize("season $currentSeason")

    init {
        getAnimeListSeasonApi()
    }

    private fun getAnimeListSeasonApi() {
        val animeApi =
            JikanApiInstance.animeApi.getSeason(currentYear, currentSeason.lowercase(Locale.getDefault()))
        val animeList : MutableList<Anime> = arrayListOf()

        _animeListSeason.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    if(animeResponse != null) {
                        val animeSeason : JsonArray? = animeResponse.getAsJsonArray("anime")
                        if(animeSeason != null) {
                            for(aux in 0 until animeSeason.size()) {
                                val animeObject : JsonObject? = animeSeason.get(aux) as JsonObject?
                                if(animeObject != null) {
                                    val anime = Anime()
                                    anime.mal_id = animeObject.get("mal_id").asString
                                    anime.image_url = animeObject.get("image_url").asString
                                    animeList.add(anime)
                                }
                            }
                        }
                        _animeListSeason.postValue(ScreenStateHelper.Success(animeList))
                    }
                } else {
                    _animeListSeason.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                _animeListSeason.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}