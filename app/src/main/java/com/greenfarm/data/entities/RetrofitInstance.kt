package com.greenfarm.data.entities

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com")
            .client(provideOkHttpClient(AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : FcmInterface by lazy {
        retrofit.create(FcmInterface::class.java)
    }

    // Client
    private fun provideOkHttpClient(
        interceptor: AppInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .run {
            addInterceptor(interceptor)
            build()
        }

    // 헤더 추가
    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain)
                : Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "key=AAAA-aGAn-I:APA91bFZu1jA-CKAhohDGsvu4mnMRJkBC8O_34LvhbJf1R4L_UE52ZNWNqCrdGqEx3qz1PvGkUJdrvr2aIp6CZJmIPtEyHLKyFj6AZT_ctxvuqNtM96TdI8oEM6pNWCUSW-09ttowDKe")
                .addHeader("Content-Type", "application/json")
                .build()
            proceed(newRequest)
        }
    }
}