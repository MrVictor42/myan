package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.AnimeListCharacterAnimeResponse
import com.victor.myan.model.Manga
import com.victor.myan.model.MangaListCharacterMangaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterMangaViewModel(private val malID : String) : ViewModel() {

    private val _characterMangaLiveData = MutableLiveData<ScreenStateHelper<List<Manga>>?>()
    val characterMangaLiveData : LiveData<ScreenStateHelper<List<Manga>>?>
        get() = _characterMangaLiveData

    init {
        getCharacterMangaApi()
    }

    @Suppress("UNCHECKED_CAST")
    class CharacterMangaFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterMangaViewModel(malID) as T
        }
    }

    private fun getCharacterMangaApi() {
        val characterApi = JikanApiInstance.characterApi.getCharacterManga(malID)

        _characterMangaLiveData.postValue(ScreenStateHelper.Loading(null))
        characterApi.enqueue(object : Callback<MangaListCharacterMangaResponse> {
            override fun onResponse(call: Call<MangaListCharacterMangaResponse>, response: Response<MangaListCharacterMangaResponse>) {
                if(response.isSuccessful) {
                    if(response.body()?.mangaography?.size == 0) {
                        _characterMangaLiveData.postValue(ScreenStateHelper.Empty("This character not appeared in manga yet :/", null))
                    } else {
                        _characterMangaLiveData.postValue(ScreenStateHelper.Success(response.body()?.mangaography))
                    }
                } else {
                    _characterMangaLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<MangaListCharacterMangaResponse>, t: Throwable) {
                _characterMangaLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}