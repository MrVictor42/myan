package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListResultResponse
import com.victor.myan.model.Manga
import com.victor.myan.model.MangaListResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreViewModel : ViewModel() {

    val resultAnimeList : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val resultMangaList : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()

    fun resultAiringApi(selected : String, genreID : Int, status : String) {
        val animeApi =
            JikanApiInstance.genreApi.resultAnimeGenreApi(
                genreID, status, "score", 0
            )
        val mangaApi =
            JikanApiInstance.genreApi.resultMangaGenreApi(
                genreID, status,"score", 0
            )

        when(selected) {
            "anime" -> {
                resultAnimeList.postValue(ScreenStateHelper.Loading(null))
                animeApi.enqueue(object : Callback<AnimeListResultResponse> {
                    override fun onResponse(call: Call<AnimeListResultResponse>, response: Response<AnimeListResultResponse>) {
                        if(response.isSuccessful) {
                            if(response.body()?.results?.size == 0) {
                                resultAnimeList.postValue(ScreenStateHelper.Empty("This section does not have anime", null))
                            } else {
                                resultAnimeList.postValue(ScreenStateHelper.Success(response.body()?.results))
                            }
                        } else {
                            resultAiringApi("anime", genreID, status)
                        }
                    }

                    override fun onFailure(call: Call<AnimeListResultResponse>, t: Throwable) {
                        resultAnimeList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                    }
                })
            }
            "manga" -> {
                resultMangaList.postValue(ScreenStateHelper.Loading(null))
                mangaApi.enqueue(object : Callback<MangaListResultResponse> {
                    override fun onResponse(call: Call<MangaListResultResponse>, response: Response<MangaListResultResponse>) {
                        if(response.isSuccessful) {
                            if(response.body()?.results?.size == 0) {
                                resultMangaList.postValue(ScreenStateHelper.Empty("This section does not have manga", null))
                            } else {
                                resultMangaList.postValue(ScreenStateHelper.Success(response.body()?.results))
                            }
                        } else {
                            resultAiringApi("manga", genreID, status)
                        }
                    }

                    override fun onFailure(call: Call<MangaListResultResponse>, t: Throwable) {
                        resultMangaList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                    }
                })
            }
        }
    }
}