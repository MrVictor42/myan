package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Manga
import com.victor.myan.model.MangaListResultResponse
import com.victor.myan.model.MangaListTopResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MangaViewModel : ViewModel() {

    val mangaListAiring : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()
    val mangaTopList : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()

    fun getMangaListTopApi() {
        val mangaApi = JikanApiInstance.mangaApi.getTopManga()

        mangaTopList.postValue(ScreenStateHelper.Loading(null))
        mangaApi.enqueue(object : Callback<MangaListTopResponse> {
            override fun onResponse(call: Call<MangaListTopResponse>, response: Response<MangaListTopResponse>) {
                if(response.isSuccessful) {
                    mangaTopList.postValue(ScreenStateHelper.Success(response.body()?.top))
                } else {
                    mangaTopList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                    getMangaListTopApi()
                }
            }

            override fun onFailure(call: Call<MangaListTopResponse>, t: Throwable) {
                mangaTopList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getMangaListAiringApi() {
        val mangaApi = JikanApiInstance.mangaApi.mangaListAiring("airing", "score")

        mangaListAiring.postValue(ScreenStateHelper.Loading(null))
        mangaApi.enqueue(object : Callback<MangaListResultResponse> {
            override fun onResponse(call: Call<MangaListResultResponse>, response: Response<MangaListResultResponse>) {
                if(response.isSuccessful) {
                    mangaListAiring.postValue(ScreenStateHelper.Success(response.body()?.results))
                } else {
                    mangaListAiring.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                    getMangaListAiringApi()
                }
            }

            override fun onFailure(call: Call<MangaListResultResponse>, t: Throwable) {
                mangaListAiring.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}