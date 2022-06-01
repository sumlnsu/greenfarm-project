package com.greenfarm.data.remote.history

import android.util.Log
import com.greenfarm.ApplicationClass
import com.greenfarm.ui.history.HistoryView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HistoryService {
    fun getHistories(HistoryView: HistoryView, userId: String) {
        val getHistoryService = ApplicationClass.retrofit.create(HistoryRetrofitInterface::class.java)

        getHistoryService.getHistory(userId).enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                Log.d("res",response.message())
                if(response.body() == null){
                    Log.d("body","have no history")
                }else{
                    val resp = response.body()!!

                    when(resp.code){
                        1000-> HistoryView.onHistorySuccess(resp.result)
                        else -> HistoryView.onHistoryFailure(resp.code, resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Log.d("${ApplicationClass.TAG}/API-ERROR", t.message.toString())
            }
        })
    }
}