package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Manga
import com.victor.myan.model.MangaListResult
import com.victor.myan.model.MangaListTop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MangaViewModel : ViewModel() {

    val manga : MutableLiveData<ScreenStateHelper<Manga>?> = MutableLiveData()
    val mangaListAiring : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()
    val mangaTopList : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()

    fun getManga(malID : Int) {
        val mangaApi = JikanApiInstance.mangaApi.getManga(malID)

        manga.postValue(ScreenStateHelper.Loading(null))
        mangaApi.enqueue(object : Callback<Manga> {
            override fun onResponse(call: Call<Manga>, response: Response<Manga>) {
                if(response.isSuccessful) {
                    manga.postValue(ScreenStateHelper.Success(response.body()))
                } else {
                    manga.postValue(ScreenStateHelper.Error(response.code().toString(),null))
                }
            }

            override fun onFailure(call: Call<Manga>, t: Throwable) {
                manga.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getMangaListTopApi() {
        val mangaApi = JikanApiInstance.mangaApi.getTopManga()

        mangaTopList.postValue(ScreenStateHelper.Loading(null))
        mangaApi.enqueue(object : Callback<MangaListTop> {
            override fun onResponse(call: Call<MangaListTop>, response: Response<MangaListTop>) {
                if(response.isSuccessful) {
                    mangaTopList.postValue(ScreenStateHelper.Success(response.body()?.top))
                } else {
                    mangaTopList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<MangaListTop>, t: Throwable) {
                mangaTopList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getMangaListAiringApi() {
        val mangaApi = JikanApiInstance.mangaApi.mangaListAiring("airing", "score")

        mangaListAiring.postValue(ScreenStateHelper.Loading(null))
        mangaApi.enqueue(object : Callback<MangaListResult> {
            override fun onResponse(call: Call<MangaListResult>, response: Response<MangaListResult>) {
                if(response.isSuccessful) {
                    mangaListAiring.postValue(ScreenStateHelper.Success(response.body()?.results))
                } else {
                    mangaListAiring.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<MangaListResult>, t: Throwable) {
                mangaListAiring.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}