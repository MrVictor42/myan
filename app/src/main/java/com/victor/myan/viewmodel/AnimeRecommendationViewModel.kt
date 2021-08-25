package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListRecommendationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeRecommendationViewModel (private val malID: String) : ViewModel() {

    private val _animeRecommendationLiveData = MutableLiveData<ScreenStateHelper<List<Anime>?>>()
    val animeRecommendationLiveData : LiveData<ScreenStateHelper<List<Anime>?>>
        get() = _animeRecommendationLiveData

    init {
        getAnimeRecommendationApi()
    }

    @Suppress("UNCHECKED_CAST")
    class AnimeRecommendationFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AnimeRecommendationViewModel(malID) as T
        }
    }

    private fun getAnimeRecommendationApi() {
        val animeApi = JikanApiInstance.animeApi.getRecommendations(malID)

        _animeRecommendationLiveData.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<AnimeListRecommendationResponse> {
            override fun onResponse(call: Call<AnimeListRecommendationResponse>,
                response: Response<AnimeListRecommendationResponse>) {
                if(response.isSuccessful) {
                    _animeRecommendationLiveData.postValue(ScreenStateHelper.Success(response.body()?.recommendations))
                } else {
                    _animeRecommendationLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeListRecommendationResponse>, t: Throwable) {
                _animeRecommendationLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}