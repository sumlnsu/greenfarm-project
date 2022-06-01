package com.greenfarm.data.entities

import com.google.gson.annotations.SerializedName

data class SearchSickNameResult(
    @SerializedName("cropName") val cropName: String = " ",
    @SerializedName("sickNameKor") var sickNameKor: String= " ",
    @SerializedName("developmentCondition") var developmentCondition: String= " ",
    @SerializedName("preventionMethod") var preventionMethod: String= " ",
    @SerializedName("symptoms") var symptoms: String= " ",
    @SerializedName("infectionRoute") var infectionRoute: String= " "
)