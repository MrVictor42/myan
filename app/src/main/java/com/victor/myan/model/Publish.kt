package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Publish (
    @SerializedName("from")
    var year : String = ""
)