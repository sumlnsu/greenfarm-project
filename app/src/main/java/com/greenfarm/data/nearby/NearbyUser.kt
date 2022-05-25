package com.greenfarm.data.nearby

import android.util.Log
import com.greenfarm.ApplicationClass
import com.greenfarm.ui.TestActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NearbyUser {
    fun getNearbyUser(testActivity: TestActivity, userId: String) {
        val getNearbyUserService = ApplicationClass.retrofit.create(NearbyUserRetrofitInterface::class.java)

        getNearbyUserService.getUser(userId).enqueue(object : Callback<NearbyUserResponse> {
            override fun onResponse(call: Call<NearbyUserResponse>, response: Response<NearbyUserResponse>) {
                Log.d("res",response.message())
                if(response.body() == null){
                    Log.d("body","no near by users")
                }else{
                    Log.d("body",response.body().toString())
                    val resp = response.body()!!

                    when(resp.code){
                        1000-> testActivity.setUserList(resp.result)
//                    else -> signUpView.onSignUpFailure(resp.code, resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<NearbyUserResponse>, t: Throwable) {
                Log.d("${ApplicationClass.TAG}/API-ERROR", t.message.toString())
            }
        })
    }
}