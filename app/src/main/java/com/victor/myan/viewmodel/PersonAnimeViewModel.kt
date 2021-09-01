package com.victor.myan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonAnimeViewModel(private val malID : String) : ViewModel() {

    private val _personAnimeLiveData = MutableLiveData<ScreenStateHelper<List<Anime>>?>()
    val personAnimeLiveData : LiveData<ScreenStateHelper<List<Anime>>?>
        get() = _personAnimeLiveData

    init {
        getPersonAnimeApi()
    }

    @Suppress("UNCHECKED_CAST")
    class PersonAnimeFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PersonAnimeViewModel(malID) as T
        }
    }

    private fun getPersonAnimeApi() {

        val actorApi = JikanApiInstance.actorApi.getActorAnime(malID)
        val animeList : MutableList<Anime> = arrayListOf()

        _personAnimeLiveData.postValue(ScreenStateHelper.Loading(null))
//        actorApi.enqueue(object : Callback<JsonObject> {
//            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                if(response.isSuccessful) {
//                    val animeResponse = response.body()
//                    if(animeResponse != null) {
//                        val animeArray : JsonArray? = animeResponse.getAsJsonArray("voice_acting_roles")
//                        if(animeArray != null) {
//                            for(aux in 0 until animeArray.size()) {
//                                val animeObject : JsonObject? = animeArray.get(aux) as JsonObject?
//                                if(animeObject != null) {
//                                    val anime : JsonObject? = animeObject.get("anime") as JsonObject?
//                                    if(anime != null) {
//                                        for(auxObject in 0 until anime.size()) {
//                                            val animePerson = Anime()
//                                            animePerson.mal_id = anime.get("image_url").asString
//
//                                            animeList.add(animePerson)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        _personAnimeLiveData.postValue(ScreenStateHelper.Success(animeList))
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//
//            }
//        })
    }
}