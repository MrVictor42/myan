package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Producer(
    @SerializedName("name")
    var name: String = ""
)