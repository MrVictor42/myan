package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Picture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PicturesViewModel(private val malID: String) : ViewModel() {

    private val _picturesLiveData = MutableLiveData<ScreenStateHelper<List<Picture>?>>()
    val picturesLiveData : LiveData<ScreenStateHelper<List<Picture>?>>
        get() = _picturesLiveData

    init {
        getPicturesApi()
    }

    @Suppress("UNCHECKED_CAST")
    class PicturesViewModelFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PicturesViewModel(malID) as T
        }
    }

    private fun getPicturesApi() {
        val picturesApi = JikanApiInstance.picturesApi.getPictures(malID)
        val pictureList : MutableList<Picture> = arrayListOf()

        _picturesLiveData.postValue(ScreenStateHelper.Loading(null))
        picturesApi.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val picturesResponse = response.body()
                    if(picturesResponse != null) {
                        val picturesArray : JsonArray? = picturesResponse.getAsJsonArray("pictures")
                        if(picturesArray != null) {
                            for(aux in 0 until picturesArray.size()) {
                                val pictureObject : JsonObject? = picturesArray.get(aux) as JsonObject?
                                if(pictureObject != null) {
                                    val picture = Picture()

                                    picture.large = pictureObject.get("large").asString
                                    pictureList.add(picture)
                                }
                            }
                        }
                    }
                    _picturesLiveData.postValue(ScreenStateHelper.Success(pictureList))
                } else {
                    _picturesLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                _picturesLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}