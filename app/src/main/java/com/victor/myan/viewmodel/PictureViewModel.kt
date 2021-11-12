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

    val pictureList : MutableLiveData<ScreenStateHelper<List<Picture>?>> = MutableLiveData()

    fun getPicturesApi(type : String, malID : Int) {
        val picturesApi = JikanApiInstance.picturesApi.getPictures(type, malID)
        val pictures : MutableList<Picture> = arrayListOf()

        pictureList.postValue(ScreenStateHelper.Loading(null))
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

                                    if(type == "anime" || type == "manga") {
                                        picture.large = pictureObject["large"].asString
                                    } else {
                                        picture.imageURL = pictureObject["image_url"].asString
                                    }
                                    pictures.add(picture)
                                }
                            }
                        }
                    }
                    pictureList.postValue(ScreenStateHelper.Success(pictures))
                } else {
                    getPicturesApi(type, malID)
                    pictureList.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                pictureList.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}