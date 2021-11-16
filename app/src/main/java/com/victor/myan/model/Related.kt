package com.victor.myan.model

import com.google.gson.annotations.SerializedName

data class Related(
    @SerializedName("Adaptation")
    var adaptations : List<Adaptation> = arrayListOf(),
)
