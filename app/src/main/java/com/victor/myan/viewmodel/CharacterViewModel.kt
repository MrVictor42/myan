package com.victor.myan.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel() {

    private val TAG = CharacterViewModel::class.java.simpleName
    val character : MutableLiveData<ScreenStateHelper<Character>?> = MutableLiveData()
    val characterList : MutableLiveData<ScreenStateHelper<List<Character>?>> = MutableLiveData()
    val characterAnimeList : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val characterMangaList : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()
    val characterVoiceList : MutableLiveData<ScreenStateHelper<List<Actor>?>> = MutableLiveData()

    fun getCharacterApi(malID : Int) {
        val characterApi = JikanApiInstance.characterApi.getCharacter(malID)

        character.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if(response.isSuccessful) {
                    character.postValue(ScreenStateHelper.Success(response.body()))
                    Log.i(TAG, "Character got with success!")
                } else {
                    character.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                    Log.e(TAG, "Error loading character")
                    getCharacterApi(malID)
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                character.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                Log.e(TAG, "Error API get CharacterApi")
            }
        })
    }

    fun getCharacterListApi(malID: Int) {
        val characterApi = JikanApiInstance.characterApi.animeCharacters(malID)

        characterList.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<AnimeCharacterResponse> {
            override fun onResponse(call: Call<AnimeCharacterResponse>, response: Response<AnimeCharacterResponse>) {
                when {
                    response.isSuccessful -> {
                        characterList.postValue(ScreenStateHelper.Success(response.body()?.characters))
                        Log.i(TAG, "Got character list with success!")
                    }
                    response.body()?.characters?.size == 0 -> {
                        characterList.postValue(ScreenStateHelper.Empty("Not found character", null))
                        Log.i(TAG, "Not have characters")
                    }
                    else -> {
                        characterList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                        Log.e(TAG, "Error loading character list")
                        getCharacterListApi(malID)
                    }
                }
            }

            override fun onFailure(call: Call<AnimeCharacterResponse>, t: Throwable) {
                characterList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                Log.e(TAG, "Error API get CharacterListApi")
            }
        })
    }

    fun getCharacterAnimeApi(malID : Int) {
        val characterApi = JikanApiInstance.characterApi.getCharacterAnime(malID)

        characterAnimeList.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<AnimeListCharacterAnimeResponse> {
            override fun onResponse(call: Call<AnimeListCharacterAnimeResponse>, response: Response<AnimeListCharacterAnimeResponse>) {
                when {
                    response.isSuccessful -> {
                        characterAnimeList.postValue(ScreenStateHelper.Success(response.body()?.animeography))
                        Log.i(TAG, "Got character anime api with success!")
                    }
                    response.body()?.animeography?.size == 0 -> {
                        characterAnimeList.postValue(ScreenStateHelper.Empty("This character not appeared on anime yet :/", null))
                        Log.i(TAG, "This character not appeared on anime yet")
                    }
                    else -> {
                        characterAnimeList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                        Log.e(TAG, "Error loading character anime api")
                        getCharacterAnimeApi(malID)
                    }
                }
            }

            override fun onFailure(call: Call<AnimeListCharacterAnimeResponse>, t: Throwable) {
                characterAnimeList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                Log.e(TAG, "Error API get CharacterAnimeApi")
            }
        })
    }

    fun getCharacterMangaApi(malID : Int) {
        val characterApi = JikanApiInstance.characterApi.getCharacterManga(malID)

        characterMangaList.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<MangaListCharacterMangaResponse> {
            override fun onResponse(call: Call<MangaListCharacterMangaResponse>, response: Response<MangaListCharacterMangaResponse>) {
                when {
                    response.isSuccessful -> {
                        characterMangaList.postValue(ScreenStateHelper.Success(response.body()?.mangaography))
                        Log.i(TAG, "Got character manga api with success!")
                    }
                    response.body()?.mangaography?.size == 0 -> {
                        characterMangaList.postValue(ScreenStateHelper.Empty("This character not appeared on manga yet :/", null))
                        Log.i(TAG, "This character not appeared on manga yet")
                    }
                    else -> {
                        characterMangaList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                        Log.e(TAG, "Error loading character manga api")
                        getCharacterMangaApi(malID)
                    }
                }
            }

            override fun onFailure(call: Call<MangaListCharacterMangaResponse>, t: Throwable) {
                characterMangaList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                Log.e(TAG, "Error API get CharacterMangaApi")
            }
        })
    }

    fun getCharacterVoiceApi(malID : Int) {
        val characterApi = JikanApiInstance.characterApi.getCharacterVoice(malID)

        characterVoiceList.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<ActorsListCharacterResponse> {
            override fun onResponse(call: Call<ActorsListCharacterResponse>, response: Response<ActorsListCharacterResponse>) {
                when {
                    response.isSuccessful -> {
                        characterVoiceList.postValue(ScreenStateHelper.Success(response.body()?.voice_actors))
                        Log.i(TAG, "Got character voice api with success!")
                    }
                    response.body()?.voice_actors?.size == 0 -> {
                        characterVoiceList.postValue(ScreenStateHelper.Empty("This character doesn't have a voices", null))
                        Log.i(TAG, "This character doesn't have a voices")
                    }
                    else -> {
                        characterVoiceList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                        Log.e(TAG, "Error loading character voice api")
                        getCharacterVoiceApi(malID)
                    }
                }
            }

            override fun onFailure(call: Call<ActorsListCharacterResponse>, t: Throwable) {
                characterVoiceList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
                Log.e(TAG, "Error API get CharacterVoiceApi")
            }
        })
    }
}