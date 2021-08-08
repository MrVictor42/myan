package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Licensor (
    @SerializedName("type")
    var type : String = "",
    @SerializedName("name")
    var name : String = ""
)
