package com.victor.myan.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Manga
import com.victor.myan.model.MangaListTopResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopMangaViewModel : ViewModel() {

    private val _mangaListTopLiveData = MutableLiveData<ScreenStateHelper<List<Manga>?>>()
    val mangaListTopLiveData : LiveData<ScreenStateHelper<List<Manga>?>>
        get() = _mangaListTopLiveData

    init {
        getMangaListTopApi()
    }

    private fun getMangaListTopApi() {
        val mangaApi = JikanApiInstance.mangaApi.getTopManga()

        _mangaListTopLiveData.postValue(ScreenStateHelper.Loading(null))
        mangaApi.enqueue(object : Callback<MangaListTopResponse> {
            override fun onResponse(call: Call<MangaListTopResponse>, response: Response<MangaListTopResponse>) {
                if(response.isSuccessful) {
                    Log.e("Response", response.body()?.top.toString())
                    _mangaListTopLiveData.postValue(ScreenStateHelper.Success(response.body()?.top))
                } else {
                    _mangaListTopLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<MangaListTopResponse>, t: Throwable) {
                _mangaListTopLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}