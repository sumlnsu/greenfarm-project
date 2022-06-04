package com.greenfarm.data.remote.Search

import com.greenfarm.data.entities.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface SearchRetrofitInterface {
    @Multipart
    @POST("/bug/search")
    fun SearchSickName(
        @Query("user_id") userId : String,
        @Query("sickName") sickName : String,
        @Query("search_time") currentTime : Long,
        @Part images : MultipartBody.Part
    ): Call<SearchResponse>
}