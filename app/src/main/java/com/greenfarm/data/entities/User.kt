package com.greenfarm.data.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id") val id: String,
    @SerializedName("user_pw") val password: String,
    @SerializedName("location") val location: String
)