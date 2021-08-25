package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListTopResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopAnimeViewModel : ViewModel() {

    private val _animeListTopLiveData = MutableLiveData<ScreenStateHelper<List<Anime>?>>()
    val animeListTopLiveData : LiveData<ScreenStateHelper<List<Anime>?>>
        get() = _animeListTopLiveData

    init {
        getAnimeListTopApi()
    }

    private fun getAnimeListTopApi() {
        val animeApi = JikanApiInstance.animeApi.getTopAnime()

        _animeListTopLiveData.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<AnimeListTopResponse> {
            override fun onResponse(call: Call<AnimeListTopResponse>, response: Response<AnimeListTopResponse>) {
                if(response.isSuccessful) {
                    _animeListTopLiveData.postValue(ScreenStateHelper.Success(response.body()?.top))
                } else {
                    _animeListTopLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeListTopResponse>, t: Throwable) {
                _animeListTopLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}