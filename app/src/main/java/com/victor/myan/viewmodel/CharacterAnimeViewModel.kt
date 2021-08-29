package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListCharacterAnimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterAnimeViewModel(private val malID : String) : ViewModel() {

    private val _characterAnimeLiveData = MutableLiveData<ScreenStateHelper<List<Anime>>?>()
    val characterAnimeLiveData : LiveData<ScreenStateHelper<List<Anime>>?>
        get() = _characterAnimeLiveData

    init {
        getCharacterAnimeApi()
    }

    @Suppress("UNCHECKED_CAST")
    class CharacterAnimeFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterAnimeViewModel(malID) as T
        }
    }

    private fun getCharacterAnimeApi() {
        val characterApi = JikanApiInstance.characterApi.getCharacterAnime(malID)

        _characterAnimeLiveData.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<AnimeListCharacterAnimeResponse> {
            override fun onResponse(call: Call<AnimeListCharacterAnimeResponse>, response: Response<AnimeListCharacterAnimeResponse>) {
                if(response.isSuccessful) {
                    if(response.body()?.animeography?.size == 0) {
                        _characterAnimeLiveData.postValue(ScreenStateHelper.Empty("This character not appeared in anime yet :/", null))
                    } else {
                        _characterAnimeLiveData.postValue(ScreenStateHelper.Success(response.body()?.animeography))
                    }
                } else {
                    _characterAnimeLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeListCharacterAnimeResponse>, t: Throwable) {
                _characterAnimeLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}