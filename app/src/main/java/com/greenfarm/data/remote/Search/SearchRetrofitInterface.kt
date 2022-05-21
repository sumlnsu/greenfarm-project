package com.greenfarm.data.remote.Search

import com.greenfarm.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchRetrofitInterface {
    @POST("/bug/search")
    fun SearchSickName(@Query("user_id") userId : String,
                       @Query("sickName") sickName : String,
    ): Call<SearchResponse>
}