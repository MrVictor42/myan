package com.victor.myan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.myan.api.JikanApiInstance
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Actor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActorViewModel(private val malID : String) : ViewModel() {

    private val _actorLiveData = MutableLiveData<ScreenStateHelper<Actor>?>()
    val actorLiveData : LiveData<ScreenStateHelper<Actor>?>
        get() = _actorLiveData

    init {
        getActorApi()
    }

    @Suppress("UNCHECKED_CAST")
    class ActorFactory(private val malID: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ActorViewModel(malID) as T
        }
    }

    private fun getActorApi() {
        val actorApi = JikanApiInstance.actorApi.getActor(malID)

        _actorLiveData.postValue(ScreenStateHelper.Loading(null))
        actorApi.enqueue(object : Callback<Actor> {
            override fun onResponse(call: Call<Actor>, response: Response<Actor>) {
                if(response.isSuccessful) {
                    _actorLiveData.postValue(ScreenStateHelper.Success(response.body()))
                } else {
                    _actorLiveData.postValue(ScreenStateHelper.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<Actor>, t: Throwable) {
                _actorLiveData.postValue(ScreenStateHelper.Error(t.message.toString(), null))
            }
        })
    }
}