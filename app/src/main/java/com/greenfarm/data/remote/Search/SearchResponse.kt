package com.greenfarm.data.remote.Search

import com.google.gson.annotations.SerializedName
import com.greenfarm.data.entities.SearchSickNameResult

data class SearchResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : SearchSickNameResult
)