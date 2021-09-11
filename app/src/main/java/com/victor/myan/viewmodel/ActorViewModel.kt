package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Actor
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActorViewModel : ViewModel() {

    val actor : MutableLiveData<ScreenStateHelper<Actor>?> = MutableLiveData()
    val actorAnimeList : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()

    fun getActorApi(malID: Int) {
        val actorApi = JikanApiInstance.actorApi.getActor(malID)

        actor.postValue(ScreenStateHelper.Loading(null))
        actorApi.enqueue(object : Callback<Actor> {
            override fun onResponse(call: Call<Actor>, response: Response<Actor>) {
                if(response.isSuccessful) {
                    actor.postValue(ScreenStateHelper.Success(response.body()))
                } else {
                    getActorApi(malID)
                    actor.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<Actor>, t: Throwable) {
                actor.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getActorAnimeApi(malID: Int) {
        val actorApi = JikanApiInstance.actorApi.getActorAnime(malID)
        val animeList : MutableList<Anime> = arrayListOf()

        actorAnimeList.postValue(ScreenStateHelper.Loading(null))
        actorApi.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val animeResponse = response.body()
                    if(animeResponse != null) {
                        val animeArray : JsonArray? = animeResponse.getAsJsonArray("voice_acting_roles")
                        if(animeArray != null) {
                            for(aux in 0 until animeArray.size()) {
                                val animeObject : JsonObject? = animeArray.get(aux) as JsonObject?
                                if(animeObject != null) {
                                    val anime : JsonObject? = animeObject.get("anime") as JsonObject?
                                    if(anime != null) {
                                        for(auxObject in 0 until anime.size()) {
                                            val animeActor = Anime()
                                            animeActor.malID = anime.get("mal_id").asInt
                                            animeActor.imageUrl = anime.get("image_url").asString
                                            animeList.add(animeActor)
                                        }
                                    }
                                }
                            }
                        }
                        actorAnimeList.postValue(ScreenStateHelper.Success(animeList))
                    }
                } else {
                    getActorAnimeApi(malID)
                    actorAnimeList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                actorAnimeList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}