package com.greenfarm.data.entities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class FirebaseViewModel(application: Application) : AndroidViewModel(application) {



    // 푸시 메세지 전송

    fun sendNotification(token:String, title:String, userId:String, message:String) {
        val data = NotificationBody.NotificationData("Green Farm","user1","test")
        val body = NotificationBody(token,data)

        viewModelScope.launch{
            RetrofitInstance.api.sendNotification(body)
        }
    }


}