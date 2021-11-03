package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Related(
    @SerializedName("Adaptation")
    var adaptations : List<Adaption> = arrayListOf(),
    @SerializedName("Spin-off")
    var spinOff : List<SpinOff> = arrayListOf()
)
