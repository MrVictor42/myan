package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Picture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureViewModel : ViewModel() {

    val picture : MutableLiveData<ScreenStateHelper<Picture>?> = MutableLiveData()

    fun getPicturesApi(type : String, malID : String) {
        val picturesApi = JikanApiInstance.picturesApi.getPictures(type, malID)

        picture.postValue(ScreenStateHelper.Loading(null))
        picturesApi.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val picturesResponse = response.body()
                    if(picturesResponse != null) {
                        val picturesArray : JsonArray? = picturesResponse.getAsJsonArray("pictures")
                        if(picturesArray != null) {
                            for(aux in 0 until 1) {
                                val pictureObject : JsonObject? = picturesArray.get(aux) as JsonObject?
                                if(pictureObject != null) {
                                    val pictureLarge = Picture()

                                    pictureLarge.large = pictureObject.get("large").asString
                                    picture.postValue(ScreenStateHelper.Success(pictureLarge))
                                }
                            }
                        }
                    }
                } else {
                    picture.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                picture.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}