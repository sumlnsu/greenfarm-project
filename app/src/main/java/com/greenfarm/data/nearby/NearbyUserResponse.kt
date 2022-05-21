package com.greenfarm.data.nearby

import com.google.gson.annotations.SerializedName

data class NearbyUserResult(@SerializedName("users") val users: ArrayList<String>)

data class NearbyUserResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<String>
)
