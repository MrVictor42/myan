package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeViewModel(private val malID: String) : ViewModel() {

    private val _animeLiveData = MutableLiveData<ScreenStateHelper<Anime>?>()
    val animeLiveData : LiveData<ScreenStateHelper<Anime>?>
        get() = _animeLiveData

    init {
        getAnimeApi()
    }

    @Suppress("UNCHECKED_CAST")
    class AnimeFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AnimeViewModel(malID) as T
        }
    }

    private fun getAnimeApi() {
        val animeApi = JikanApiInstance.animeApi.getAnime(malID)

        _animeLiveData.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<Anime> {
            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if(response.isSuccessful) {
                    _animeLiveData.postValue(ScreenStateHelper.Success(response.body()))
                } else {
                    _animeLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<Anime>, t: Throwable) {
                _animeLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}