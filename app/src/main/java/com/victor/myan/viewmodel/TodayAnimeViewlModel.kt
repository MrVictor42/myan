package com.victor.myan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.myan.api.RetroInstance
import com.victor.myan.api.RetroService
import com.victor.myan.model.RecyclerList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodayAnimeViewlModel: ViewModel() {
    lateinit var recyclerListLiveData : MutableLiveData<RecyclerList>

    init {
        recyclerListLiveData = MutableLiveData()
    }

    fun getRecyclerListObverser(): MutableLiveData<RecyclerList> {
        return recyclerListLiveData
    }

    fun makeApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
            val response = retroInstance.getDataFromApi("ny")
            recyclerListLiveData.postValue(response )
        }
    }
}