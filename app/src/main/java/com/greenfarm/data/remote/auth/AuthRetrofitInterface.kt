package com.greenfarm.data.remote.auth

import com.greenfarm.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/user/sign-up")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("/user/log-in")
    fun login(@Body user: User): Call<AuthResponse>

    @GET("/users/auto-login")
    fun autoLogin(): Call<AuthResponse>
}