package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreViewModel : ViewModel() {

    val resultAnimeList : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val resultMangaList : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()

    fun resultSearchApi(selected : String, genreID : Int, status : String) {
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
                animeApi.enqueue(object : Callback<AnimeListResult> {
                    override fun onResponse(call: Call<AnimeListResult>, response: Response<AnimeListResult>) {
                        if(response.isSuccessful) {
                            if(response.body()?.results?.size == 0 || response.body()?.results.toString().isNullOrEmpty()) {
                                resultAnimeList.postValue(ScreenStateHelper.Empty("This section does not have anime", null))
                            } else {
                                resultAnimeList.postValue(ScreenStateHelper.Success(response.body()?.results))
                            }
                        }
                    }

                    override fun onFailure(call: Call<AnimeListResult>, t: Throwable) {
                        resultAnimeList.postValue(ScreenStateHelper.Error("Something wrong has happened, try again", null))
                    }
                })
            }
            "manga" -> {
                resultMangaList.postValue(ScreenStateHelper.Loading(null))
                mangaApi.enqueue(object : Callback<MangaListResult> {
                    override fun onResponse(call: Call<MangaListResult>, response: Response<MangaListResult>) {
                        if(response.isSuccessful) {
                            if(response.body()?.results?.size == 0 || response.body()?.results.toString().isNullOrEmpty()) {
                                resultMangaList.postValue(ScreenStateHelper.Empty("This section does not have manga", null))
                            } else {
                                resultMangaList.postValue(ScreenStateHelper.Success(response.body()?.results))
                            }
                        }
                    }

                    override fun onFailure(call: Call<MangaListResult>, t: Throwable) {
                        resultMangaList.postValue(ScreenStateHelper.Error("Something wrong has happened, try again", null))
                    }
                })
            }
        }
    }
}