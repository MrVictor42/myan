package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel() {

    val character : MutableLiveData<ScreenStateHelper<Character>?> = MutableLiveData()
    val characterList : MutableLiveData<ScreenStateHelper<List<Character>?>> = MutableLiveData()
    val characterAnimeList : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()
    val characterMangaList : MutableLiveData<ScreenStateHelper<List<Manga>?>> = MutableLiveData()
    val characterVoiceList : MutableLiveData<ScreenStateHelper<List<Actor>?>> = MutableLiveData()

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

    fun getCharacterAnimeApi(malID: String) {
        val characterApi = JikanApiInstance.characterApi.getCharacterAnime(malID)

        characterAnimeList.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<AnimeListCharacterAnimeResponse> {
            override fun onResponse(call: Call<AnimeListCharacterAnimeResponse>, response: Response<AnimeListCharacterAnimeResponse>) {
                if(response.isSuccessful) {
                    if(response.body()?.animeography?.size == 0) {
                        characterAnimeList.postValue(ScreenStateHelper.Empty("This character not appeared on anime yet :/", null))
                    } else {
                        characterAnimeList.postValue(ScreenStateHelper.Success(response.body()?.animeography))
                    }
                } else {
                    characterAnimeList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<AnimeListCharacterAnimeResponse>, t: Throwable) {
                characterAnimeList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getCharacterMangaApi(malID: String) {
        val characterApi = JikanApiInstance.characterApi.getCharacterManga(malID)

        characterMangaList.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<MangaListCharacterMangaResponse> {
            override fun onResponse(call: Call<MangaListCharacterMangaResponse>, response: Response<MangaListCharacterMangaResponse>) {
                if(response.isSuccessful) {
                    if(response.body()?.mangaography?.size == 0) {
                        characterMangaList.postValue(ScreenStateHelper.Empty("This character not appeared on manga yet :/", null))
                    } else {
                        characterMangaList.postValue(ScreenStateHelper.Success(response.body()?.mangaography))
                    }
                } else {
                    characterMangaList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<MangaListCharacterMangaResponse>, t: Throwable) {
                characterMangaList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }

    fun getCharacterVoiceApi(malID: String) {
        val characterApi = JikanApiInstance.characterApi.getCharacterVoice(malID)

        characterVoiceList.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<ActorsListCharacterResponse> {
            override fun onResponse(call: Call<ActorsListCharacterResponse>, response: Response<ActorsListCharacterResponse>) {
                if(response.isSuccessful) {
                    if(response.body()?.voice_actors?.size == 0) {
                        characterVoiceList.postValue(ScreenStateHelper.Empty("This character doesn't have a voice", null))
                    } else {
                        characterVoiceList.postValue(ScreenStateHelper.Success(response.body()?.voice_actors))
                    }
                } else {
                    characterVoiceList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<ActorsListCharacterResponse>, t: Throwable) {
                characterVoiceList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}