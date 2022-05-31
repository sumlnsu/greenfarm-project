package com.greenfarm.ui.main

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.greenfarm.data.entities.NotificationBody
import com.greenfarm.data.entities.RetrofitInstance
import com.greenfarm.databinding.ActivityMainBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.MyFirebaseMessagingService
import com.greenfarm.utils.getJwt
import com.greenfarm.utils.getUserId
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    val myResponse : MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    override fun initAfterBinding() {
        var token: String? = null
        var userId : String ? = null
        userId = getUserId()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(MyFirebaseMessagingService.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result

            // Log and toast
            Log.d("token",token.toString())
            Log.d("jwt", getJwt().toString())
//            Log.d("jwt",intent.getStringExtra("jwt").toString())
            val database : FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef = database.getReference("tokens")
            myRef.child(userId!!).setValue(token.toString())
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