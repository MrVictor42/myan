package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Character
import com.victor.myan.model.AnimeCharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeCharacterViewModel(private val malID: String) : ViewModel() {

    private val _characterLiveData = MutableLiveData<ScreenStateHelper<List<Character>?>>()
    val characterLiveData : LiveData<ScreenStateHelper<List<Character>?>>
        get() = _characterLiveData

    init {
        getCharactersApi()
    }

    @Suppress("UNCHECKED_CAST")
    class CharacterFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AnimeCharacterViewModel(malID) as T
        }
    }

    private fun getCharactersApi() {
        val characterApi = JikanApiInstance.characterApi.animeCharacters(malID)

        _characterLiveData.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<AnimeCharacterResponse> {
            override fun onResponse(call: Call<AnimeCharacterResponse>, response: Response<AnimeCharacterResponse>) {
                if(response.isSuccessful) {
                    _characterLiveData.postValue(ScreenStateHelper.Success(response.body()?.characters))
                } else {
                    _characterLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeCharacterResponse>, t: Throwable) {
                _characterLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}