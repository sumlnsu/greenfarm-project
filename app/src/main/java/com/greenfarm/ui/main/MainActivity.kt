package com.greenfarm.ui.main

import android.content.Intent
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.greenfarm.databinding.ActivityMainBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.MyFirebaseMessagingService

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {


    override fun initAfterBinding() {
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(MyFirebaseMessagingService.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result

            // Log and toast
            Log.d("token",token.toString())
            val database : FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef = database.getReference("tokens")
            myRef.child("user1").setValue(token.toString())
        })

        binding.mainSearchBtIv.setOnClickListener{
            val  intent= Intent(this, SearchActivity::class.java)
            startActivity(intent)

        }

        binding.mainHistoryBtIv.setOnClickListener{
            val  intent= Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}