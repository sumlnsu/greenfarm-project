package com.greenfarm.data.remote.history

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HistoryRetrofitInterface {
    @GET("/user/history")
    fun getHistory(@Query("user_id") userId : String,
    ): Call<HistoryResponse>
}