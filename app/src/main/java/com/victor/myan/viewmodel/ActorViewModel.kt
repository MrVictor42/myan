package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Actor
import com.victor.myan.model.Anime
import com.victor.myan.model.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActorViewModel : ViewModel() {

    val actor : MutableLiveData<ScreenStateHelper<Actor>?> = MutableLiveData()
    val actorAnimeList : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val actorCharacterList : MutableLiveData<ScreenStateHelper<List<Character>?>> = MutableLiveData()

    fun getActorApi(malID: Int) {
        val actorApi = JikanApiInstance.actorApi.getActor(malID)

        actor.postValue(ScreenStateHelper.Loading(null))
        actorApi.enqueue(object : Callback<Actor> {
            override fun onResponse(call: Call<Actor>, response: Response<Actor>) {
                if(response.isSuccessful) {
                    actor.postValue(ScreenStateHelper.Success(response.body()))
                } else {
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
                val animeResponse = response.body()
                if (animeResponse != null) {
                    val animeArray: JsonArray? = animeResponse.getAsJsonArray("voice_acting_roles")
                    if (animeArray != null) {
                        for (aux in 0 until animeArray.size()) {
                            val animeObject: JsonObject? = animeArray.get(aux) as JsonObject?
                            if(animeObject != null) {
                                val anime: JsonObject? = animeObject.get("anime") as JsonObject?
                                if(anime != null) {
                                    val animeActor = Anime()
                                    animeActor.malID = anime["mal_id"].asInt
                                    animeActor.imageURL = anime["image_url"].asString
                                    animeList.add(animeActor)
                                }
                            }
                        }
                    }
                    actorAnimeList.postValue(ScreenStateHelper.Success(animeList))
                } else {
                    actorAnimeList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                actorAnimeList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getActorCharacterApi(malID: Int) {
        val actorApi = JikanApiInstance.actorApi.getActorAnime(malID)
        val characterList : MutableList<Character> = arrayListOf()

        actorCharacterList.postValue(ScreenStateHelper.Loading(null))
        actorApi.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val characterResponse = response.body()
                if (characterResponse != null) {
                    val characterArray: JsonArray? = characterResponse.getAsJsonArray("voice_acting_roles")
                    if (characterArray != null) {
                        for (aux in 0 until characterArray.size()) {
                            val characterObject: JsonObject? = characterArray.get(aux) as JsonObject?
                            if(characterObject != null) {
                                val character : JsonObject? = characterObject.get("character") as JsonObject?
                                if(character != null) {
                                    val characterActor = Character()

                                    characterActor.malID = character["mal_id"].asInt
                                    characterActor.imageURL = character["image_url"].asString
                                    characterActor.name = character["name"].asString
                                    characterList.add(characterActor)
                                }
                            }
                        }
                    }
                    actorCharacterList.postValue(ScreenStateHelper.Success(characterList))
                } else {
                    actorCharacterList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                actorAnimeList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}