package com.greenfarm.data.remote.history

import com.google.gson.annotations.SerializedName

data class HistoryResult(
    @SerializedName("cropName") val cropName: String,
    @SerializedName("sickNameKor") val sickNameKor: String,
    @SerializedName("developmentCondition") val developmentCondition: String,
    @SerializedName("preventionMethod") val preventionMethod: String,
    @SerializedName("symptoms") val symptoms: String,
    @SerializedName("infectionRoute") val infectionRoute: String,
    @SerializedName("imgPath") val imgPath: String,
    @SerializedName("search_time") val currentTime: String
)
