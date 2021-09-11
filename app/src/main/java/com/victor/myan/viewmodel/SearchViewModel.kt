package com.victor.myan.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListSearchResponse
import com.victor.myan.model.Manga
import com.victor.myan.model.MangaListSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    val resultsAnimeMovie : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val resultsManga : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()

    fun getResultsListApi(choice : String, query : String, limit : Int) {
        when(choice) {
            "Manga" -> {
                val searchManga = JikanApiInstance.searchApi.searchManga(query, limit)

                resultsManga.postValue(ScreenStateHelper.Loading(null))
                searchManga.enqueue(object : Callback<MangaListSearchResponse> {
                    override fun onResponse(call: Call<MangaListSearchResponse>, response: Response<MangaListSearchResponse>) {
                        when {
                            response.isSuccessful -> {
                                resultsManga.postValue(ScreenStateHelper.Success(response.body()?.results))
                                Log.e("response", response.body()?.results.toString())
                            }
                            response.body()?.results?.size == 0 -> {
                                resultsManga.postValue(ScreenStateHelper.Empty("Not found results... Try another again", null))
                            }
                            else -> {
                                getResultsListApi(choice, query, limit)
                            }
                        }
                    }

                    override fun onFailure(call: Call<MangaListSearchResponse>, t: Throwable) {
                        resultsManga.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                    }
                })
            } else -> {
                val searchAnime = JikanApiInstance.searchApi.searchAnime(query, limit)

                resultsAnimeMovie.postValue(ScreenStateHelper.Loading(null))
                searchAnime.enqueue(object : Callback<AnimeListSearchResponse> {
                    override fun onResponse(call: Call<AnimeListSearchResponse>, response: Response<AnimeListSearchResponse>) {
                        when {
                            response.isSuccessful -> {
                                resultsAnimeMovie.postValue(ScreenStateHelper.Success(response.body()?.results))
                            }
                            response.body()?.results?.size == 0 -> {
                                resultsAnimeMovie.postValue(ScreenStateHelper.Empty("Not found results...", null))
                            }
                            else -> {
                                getResultsListApi(choice, query, limit)
                            }
                        }
                    }

                    override fun onFailure(call: Call<AnimeListSearchResponse>, t: Throwable) {
                        resultsAnimeMovie.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                    }
                })
            }
        }
    }
}