package com.greenfarm.data.remote.Search

import android.util.Log
import com.greenfarm.ApplicationClass.Companion.TAG
import com.greenfarm.ApplicationClass.Companion.retrofit
import com.greenfarm.data.entities.User
import com.greenfarm.ui.SearchSickNameView
import com.greenfarm.ui.login.LoginView
import com.greenfarm.ui.signup.SignUpView
import com.greenfarm.ui.splash.SplashView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

object SearchService {
    fun SearchSickName(searchSickNameView: SearchSickNameView ,userId : String, sickName : String, file : File, currentTime:Long) {
        val searchService = retrofit.create(SearchRetrofitInterface::class.java)
        var requestFile : RequestBody = file.asRequestBody("image/png".toMediaTypeOrNull())
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("images", file.name, requestFile)

        searchSickNameView.onSearchSickNameLoading()

        searchService.SearchSickName(userId, sickName, currentTime, body).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1000 -> searchSickNameView.onSearchSickNameSuccess(resp.result)
                    else -> searchSickNameView.onSearchSickNameFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                searchSickNameView.onSearchSickNameFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

}