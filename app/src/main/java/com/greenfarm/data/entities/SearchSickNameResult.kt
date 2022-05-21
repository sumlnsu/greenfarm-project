package com.greenfarm.data.entities

import com.google.gson.annotations.SerializedName

data class SearchSickNameResult(
    @SerializedName("cropName") val cropName: String,
    @SerializedName("sickNameKor") val sickNameKor: String,
    @SerializedName("developmentCondition") val developmentCondition: String,
    @SerializedName("preventionMethod") val preventionMethod: String,
    @SerializedName("symptoms") val symptoms: String,
    @SerializedName("infectionRoute") val infectionRoute: String
)