package com.greenfarm.data.remote.auth

import com.google.gson.annotations.SerializedName

data class LoginResult(@SerializedName("jwt") val jwt: String, @SerializedName("user_id") val userid: String)

data class SignUpResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: String?
)

data class LoginResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: LoginResult?
)