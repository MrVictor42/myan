package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenState
import com.victor.myan.model.Character
import com.victor.myan.model.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel(private val malID: String) : ViewModel() {

    private val _characterLiveData = MutableLiveData<ScreenState<List<Character>?>>()
    val characterLiveData : LiveData<ScreenState<List<Character>?>>
        get() = _characterLiveData

    init {
        getCharactersApi()
    }

    @Suppress("UNCHECKED_CAST")
    class CharacterFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterViewModel(malID) as T
        }
    }

    private fun getCharactersApi() {
        val characterApi = JikanApiInstance.characterApi.fetchCharacters(malID)
        _characterLiveData.postValue(ScreenState.Loading(null))

        characterApi.enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if(response.isSuccessful) {
                    _characterLiveData.postValue(ScreenState.Success(response.body()?.characters))
                } else {
                    _characterLiveData.postValue(ScreenState.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                _characterLiveData.postValue(ScreenState.Error(t.message.toString(), null))
            }
        })
    }
}