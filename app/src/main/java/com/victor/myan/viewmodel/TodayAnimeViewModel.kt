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

class TodayAnimeViewModel : ViewModel() {

    val auxFunctionsHelper = AuxFunctionsHelper()
    private val currentDay = auxFunctionsHelper.getCurrentDay().lowercase(Locale.getDefault())
    val currentDayFormatted = auxFunctionsHelper.capitalize("today anime : $currentDay")

    private val _animeToday = MutableLiveData<ScreenStateHelper<List<Anime>?>>()
    val animeToday : LiveData<ScreenStateHelper<List<Anime>?>>
        get() = _animeToday

    init {
        getAnimeTodayApi()
    }

    private fun getAnimeTodayApi() {
        val animeApi = JikanApiInstance.animeApi.getTodayAnime(currentDay)
        val animeList : MutableList<Anime> = arrayListOf()

        _animeToday.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    if(animeResponse != null) {
                        val dayAnime : JsonArray? = animeResponse.getAsJsonArray(currentDay)
                        if(dayAnime != null) {
                            for(aux in 0 until dayAnime.size()) {
                                val animeObject : JsonObject? = dayAnime.get(aux) as JsonObject?
                                if(animeObject != null) {
                                    val anime = Anime()
                                    anime.mal_id = animeObject.get("mal_id").asString
                                    anime.image_url = animeObject.get("image_url").asString
                                    animeList.add(anime)
                                }
                            }
                        }
                    }
                    _animeToday.postValue(ScreenStateHelper.Success(animeList))
                } else {
                    _animeToday.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                _animeToday.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}