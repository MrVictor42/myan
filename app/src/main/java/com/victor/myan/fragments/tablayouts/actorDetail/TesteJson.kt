package com.victor.myan.fragments.tablayouts.actorDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.R
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.VoiceActingRolesAnimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TesteJson : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste_json)

        val malID = 21971
        val actorApi = JikanApiInstance.actorApi.getActorAnime(malID)
        val animeList: MutableList<Anime> = arrayListOf()

        actorApi.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val animeResponse = response.body()
                if (animeResponse != null) {
                    val animeArray: JsonArray? = animeResponse.getAsJsonArray("voice_acting_roles")
                    if (animeArray != null) {
                        for (aux in 0 until animeArray.size()) {
                            val animeObject: JsonObject? = animeArray.get(aux) as JsonObject?
                            if (animeObject != null) {
                                val anime: JsonObject? = animeObject.get("anime") as JsonObject?
                                if (anime != null) {
                                    for (auxObject in 0 until anime.size()) {
                                        val animeActor = Anime()
                                        animeActor.malID = anime.get("mal_id").asInt
                                        animeActor.imageUrl = anime.get("image_url").asString
                                        animeActor.title = anime.get("name").asString
//                                        animeList.add(animeActor)
//
                                        Log.e("ANIME", animeActor.title)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
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
//                                            val animeActor = Anime()
//                                            animeActor.malID = anime.get("mal_id").asInt
//                                            animeActor.imageUrl = anime.get("image_url").asString
//                                            animeList.add(animeActor)
//                                        }
//                                    }
//                                }
//                            }
//                        }
////                        actorAnimeList.postValue(ScreenStateHelper.Success(animeList))
//                    }
//                } else {
////                    getActorAnimeApi(malID)
////                    actorAnimeList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
//                }
//            }
//
//            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
////                actorAnimeList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
//            }
//        })
