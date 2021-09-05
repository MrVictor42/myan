package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.AnimeCharacterResponse
import com.victor.myan.model.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel() {

    val character : MutableLiveData<ScreenStateHelper<Character>?> = MutableLiveData()
    val characterList : MutableLiveData<ScreenStateHelper<List<Character>?>> = MutableLiveData()

    fun getCharacterApi(malID : String) {
        val characterApi = JikanApiInstance.characterApi.getCharacter(malID)

        character.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if(response.isSuccessful) {
                    character.postValue(ScreenStateHelper.Success(response.body()))
                } else {
                    character.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                character.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getCharacterListApi(malID: String) {
        val characterApi = JikanApiInstance.characterApi.animeCharacters(malID)

        characterList.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<AnimeCharacterResponse> {
            override fun onResponse(call: Call<AnimeCharacterResponse>, response: Response<AnimeCharacterResponse>) {
                if(response.isSuccessful) {
                    characterList.postValue(ScreenStateHelper.Success(response.body()?.characters))
                } else {
                    characterList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeCharacterResponse>, t: Throwable) {
                characterList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}