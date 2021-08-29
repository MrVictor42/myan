package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Actor
import com.victor.myan.model.ActorsListCharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterVoiceViewModel(private val malID : String) : ViewModel() {

    private val _characterVoiceLiveData = MutableLiveData<ScreenStateHelper<List<Actor>>?>()
    val characterVoiceLiveData : LiveData<ScreenStateHelper<List<Actor>>?>
        get() = _characterVoiceLiveData

    init {
        getCharacterVoiceApi()
    }

    @Suppress("UNCHECKED_CAST")
    class CharacterVoiceFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterVoiceViewModel(malID) as T
        }
    }

    private fun getCharacterVoiceApi() {
        val characterApi = JikanApiInstance.characterApi.getCharacterVoice(malID)

        _characterVoiceLiveData.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<ActorsListCharacterResponse> {
            override fun onResponse(call: Call<ActorsListCharacterResponse>, response: Response<ActorsListCharacterResponse>) {
                if(response.isSuccessful) {
                    if(response.body()?.voice_actors?.size == 0) {
                        _characterVoiceLiveData.postValue(ScreenStateHelper.Empty("This character doesn't have a voice", null))
                    } else {
                        _characterVoiceLiveData.postValue(ScreenStateHelper.Success(response.body()?.voice_actors))
                    }
                } else {
                    _characterVoiceLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<ActorsListCharacterResponse>, t: Throwable) {
                _characterVoiceLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}