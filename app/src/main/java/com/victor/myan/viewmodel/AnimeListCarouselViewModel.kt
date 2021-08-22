package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListCarouselResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeListCarouselViewModel : ViewModel() {

    private val _listAnimeLiveData = MutableLiveData<ScreenStateHelper<List<Anime>?>>()
    private val limit = 12
    val listAnimeLiveData : LiveData<ScreenStateHelper<List<Anime>?>>
        get() = _listAnimeLiveData

    init {
        getAnimeListCarouselApi()
    }

    private fun getAnimeListCarouselApi() {
        val animeApi = JikanApiInstance.animeApi.animeListCarousel("airing", "score", limit)
        _listAnimeLiveData.postValue(ScreenStateHelper.Loading(null))

        animeApi.enqueue(object : Callback<AnimeListCarouselResponse> {
            override fun onResponse(call: Call<AnimeListCarouselResponse>, response: Response<AnimeListCarouselResponse>) {
                if(response.isSuccessful) {
                    _listAnimeLiveData.postValue(ScreenStateHelper.Success(response.body()?.results))
                } else {
                    _listAnimeLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeListCarouselResponse>, t: Throwable) {
                _listAnimeLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}