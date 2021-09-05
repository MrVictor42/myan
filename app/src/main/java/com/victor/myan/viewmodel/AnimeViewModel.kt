package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListAiringResponse
import com.victor.myan.model.AnimeListRecommendationResponse
import com.victor.myan.model.AnimeListTopResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class AnimeViewModel : ViewModel() {

    val anime : MutableLiveData<ScreenStateHelper<Anime>?> = MutableLiveData()
    val animeListAiring : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val animeListToday : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val animeListSeason : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val animeListTop : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val animeRecommendationList : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()

    private val auxFunctionsHelper = AuxFunctionsHelper()
    private val currentDay = auxFunctionsHelper.getCurrentDay().lowercase(Locale.getDefault())
    private val currentSeason = auxFunctionsHelper.getSeason()

    val currentDayFormatted = auxFunctionsHelper.capitalize("today anime : $currentDay")
    val currentSeasonFormatted = auxFunctionsHelper.capitalize("season $currentSeason")

    fun getAnimeListAiringApi() {
        val animeApi = JikanApiInstance.animeApi.animeListAiring("airing", "score")

        animeListAiring.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<AnimeListAiringResponse> {
            override fun onResponse(call: Call<AnimeListAiringResponse>, response: Response<AnimeListAiringResponse>) {
                if(response.isSuccessful) {
                    animeListAiring.postValue(ScreenStateHelper.Success(response.body()?.results))
                } else {
                    animeListAiring.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeListAiringResponse>, t: Throwable) {
                animeListAiring.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getAnimeListTodayApi() {
        val animeApi = JikanApiInstance.animeApi.getTodayAnime(currentDay)
        val animeList : MutableList<Anime> = arrayListOf()

        animeListToday.postValue(ScreenStateHelper.Loading(null))
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
                    animeListToday.postValue(ScreenStateHelper.Success(animeList))
                } else {
                    animeListToday.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                animeListToday.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getAnimeListSeasonApi() {
        val currentYear = auxFunctionsHelper.getCurrentYear()
        val animeApi =
            JikanApiInstance.animeApi.getSeason(currentYear, currentSeason.lowercase(Locale.getDefault()))
        val animeList : MutableList<Anime> = arrayListOf()

        animeListSeason.postValue(ScreenStateHelper.Loading(null))
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
                        animeListSeason.postValue(ScreenStateHelper.Success(animeList))
                    }
                } else {
                    animeListSeason.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                animeListSeason.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getAnimeListTopApi() {
        val animeApi = JikanApiInstance.animeApi.getTopAnime()

        animeListTop.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<AnimeListTopResponse> {
            override fun onResponse(call: Call<AnimeListTopResponse>, response: Response<AnimeListTopResponse>) {
                if(response.isSuccessful) {
                    animeListTop.postValue(ScreenStateHelper.Success(response.body()?.top))
                } else {
                    animeListTop.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeListTopResponse>, t: Throwable) {
                animeListTop.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getAnimeApi(malID : String) {
        val animeApi = JikanApiInstance.animeApi.getAnime(malID)

        anime.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<Anime> {
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if(response.isSuccessful) {
                    anime.postValue(ScreenStateHelper.Success(response.body()))
                } else {
                    anime.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                anime.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getAnimeRecommendationApi(malID: String) {
        val animeApi = JikanApiInstance.animeApi.getRecommendations(malID)

        animeRecommendationList.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<AnimeListRecommendationResponse> {
            override fun onResponse(call: Call<AnimeListRecommendationResponse>,
                                    response: Response<AnimeListRecommendationResponse>) {
                if(response.isSuccessful) {
                    animeRecommendationList.postValue(ScreenStateHelper.Success(response.body()?.recommendations))
                } else {
                    animeRecommendationList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeListRecommendationResponse>, t: Throwable) {
                animeRecommendationList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}