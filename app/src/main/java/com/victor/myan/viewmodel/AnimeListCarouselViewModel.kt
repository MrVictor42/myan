//package com.victor.myan.viewmodel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.victor.myan.api.JikanApiInstance
//import com.victor.myan.helper.ScreenStateHelper
//import com.victor.myan.model.Anime
//import com.victor.myan.model.AnimeListCarouselResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class AnimeListCarouselViewModel : ViewModel() {
//
//    private val _animeListCarousel = MutableLiveData<ScreenStateHelper<List<Anime>?>>()
//    private val limit = 12
//    val animeListCarouselLiveData : LiveData<ScreenStateHelper<List<Anime>?>>
//        get() = _animeListCarousel
//
////    init {
////        getAnimeListCarouselApi()
////    }
//
//    fun getAnimeListCarouselApi() {
//        val animeApi = JikanApiInstance.animeApi.animeListCarousel("airing", "score", limit)
//
//        _animeListCarousel.postValue(ScreenStateHelper.Loading(null))
//        animeApi.enqueue(object : Callback<AnimeListCarouselResponse> {
//            override fun onResponse(call: Call<AnimeListCarouselResponse>, response: Response<AnimeListCarouselResponse>) {
//                if(response.isSuccessful) {
//                    _animeListCarousel.postValue(ScreenStateHelper.Success(response.body()?.results))
//                } else {
//                    _animeListCarousel.postValue(ScreenStateHelper.Error(response.code().toString(), null))
//                }
//            }
//
//            override fun onFailure(call: Call<AnimeListCarouselResponse>, t: Throwable) {
//                _animeListCarousel.postValue(ScreenStateHelper.Error(t.message.toString(), null))
//            }
//        })
//    }
//}
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

    var animeListCarousel : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()

    fun getAnimeListCarouselObserver() : MutableLiveData<ScreenStateHelper<List<Anime>?>> {
        return animeListCarousel
    }

    fun getAnimeListAiringCarouselApi(limit : Int) {
        val animeApi = JikanApiInstance.animeApi.animeListCarousel("airing", "score", limit)

        animeListCarousel.postValue(ScreenStateHelper.Loading(null))
        animeApi.enqueue(object : Callback<AnimeListCarouselResponse> {
            override fun onResponse(call: Call<AnimeListCarouselResponse>, response: Response<AnimeListCarouselResponse>) {
                if(response.isSuccessful) {
                    animeListCarousel.postValue(ScreenStateHelper.Success(response.body()?.results))
                } else {
                    animeListCarousel.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeListCarouselResponse>, t: Throwable) {
                animeListCarousel.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

//    private val _animeListCarouselLiveData = MutableLiveData<ScreenStateHelper<List<Anime>?>>()
//    private val limit = 12
//    val animeListCarouselLiveData : LiveData<ScreenStateHelper<List<Anime>?>>
//        get() = _animeListCarouselLiveData
//
//    init {
//        getAnimeListCarouselApi()
//    }
//
//    private fun getAnimeListCarouselApi() {
//        val animeApi = JikanApiInstance.animeApi.animeListCarousel("airing", "score", limit)
//
//        _animeListCarouselLiveData.postValue(ScreenStateHelper.Loading(null))
//        animeApi.enqueue(object : Callback<AnimeListCarouselResponse> {
//            override fun onResponse(call: Call<AnimeListCarouselResponse>, response: Response<AnimeListCarouselResponse>) {
//                if(response.isSuccessful) {
//                    _animeListCarouselLiveData.postValue(ScreenStateHelper.Success(response.body()?.results))
//                } else {
//                    _animeListCarouselLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
//                }
//            }
//
//            override fun onFailure(call: Call<AnimeListCarouselResponse>, t: Throwable) {
//                _animeListCarouselLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
//            }
//        })
//    }
}