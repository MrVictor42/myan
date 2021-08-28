package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel(private val malID : String) : ViewModel() {

    private val _characterLiveData = MutableLiveData<ScreenStateHelper<Character>?>()
    val characterLiveData : LiveData<ScreenStateHelper<Character>?>
        get() = _characterLiveData

    init {
        getCharacterApi()
    }

    @Suppress("UNCHECKED_CAST")
    class CharacterFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterViewModel(malID) as T
        }
    }

    private fun getCharacterApi() {
        val characterApi = JikanApiInstance.characterApi.getCharacter(malID)

        _characterLiveData.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if(response.isSuccessful) {
                    _characterLiveData.postValue(ScreenStateHelper.Success(response.body()))
                } else {
                    _characterLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                _characterLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}