package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Related(
    @SerializedName("Adaptation")
    var adaptations : List<Adaptation> = arrayListOf(),
    @SerializedName("Spin-off")
    var spinOff : List<SpinOff> = arrayListOf()
)
