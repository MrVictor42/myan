package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victor.myan.api.CharacterApi
import com.victor.myan.helper.JikanApiInstanceHelper
import com.victor.myan.model.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel() {

    private val _response = MutableLiveData<CharacterResponse>()
    val response: LiveData<CharacterResponse>
        get() = _response

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _failed = MutableLiveData<String>()
    val failed: LiveData<String>
        get() = _failed

    init {
        _loading.value = true
        getApiResponse("21")
    }

    private fun getApiResponse(malID : String) {
        val characterApi = JikanApiInstanceHelper.getJikanApiInstance().create(CharacterApi::class.java)

        characterApi.getCharactersStaff(malID).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                _response.value = response.body()
                _loading.value = false
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                _loading.value = false
                _failed.value = t.localizedMessage
            }
        })
    }
}