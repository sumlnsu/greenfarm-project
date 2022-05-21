package com.greenfarm.data.nearby

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NearbyUserRetrofitInterface {
    @GET("/user/nearby")
    fun getUser(

        @Query("user_id") userId: String): Call<NearbyUserResponse>
}