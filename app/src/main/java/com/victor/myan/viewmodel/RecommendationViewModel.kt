package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListRecommendation
import com.victor.myan.model.Manga
import com.victor.myan.model.MangaListRecommendation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationViewModel : ViewModel() {

    val animeList : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val mangaList : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()

    fun getRecommendationApi(type : String, malID : Int) {
        when(type) {
            "anime" -> {
                val animeApi = JikanApiInstance.recommendationApi.getAnimeRecommendations(malID)

                animeList.postValue(ScreenStateHelper.Loading(null))
                animeApi.enqueue(object : Callback<AnimeListRecommendation> {
                    override fun onResponse(call: Call<AnimeListRecommendation>, response: Response<AnimeListRecommendation>) {
                        if(response.isSuccessful) {
                            animeList.postValue(ScreenStateHelper.Success(response.body()?.recommendations))
                        } else {
                            animeList.postValue(ScreenStateHelper.Error("Try again later", null))
                        }
                    }

                    override fun onFailure(call: Call<AnimeListRecommendation>, t: Throwable) {
                        animeList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                    }
                })
            }
            "manga" -> {
                val mangaApi = JikanApiInstance.recommendationApi.getMangaRecommendations(malID)

                mangaList.postValue(ScreenStateHelper.Loading(null))
                mangaApi.enqueue(object : Callback<MangaListRecommendation> {
                    override fun onResponse(call: Call<MangaListRecommendation>, response: Response<MangaListRecommendation>) {
                        if(response.isSuccessful) {
                            mangaList.postValue(ScreenStateHelper.Success(response.body()?.recommendations))
                        } else {
                            mangaList.postValue(ScreenStateHelper.Error("Try again later", null))
                        }
                    }

                    override fun onFailure(call: Call<MangaListRecommendation>, t: Throwable) {
                        mangaList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                    }
                })
            }
        }
    }
}