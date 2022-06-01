package com.greenfarm.ui.main

import android.util.Log
import com.greenfarm.data.remote.history.HistoryResult
import com.greenfarm.data.remote.history.HistoryService
import com.greenfarm.databinding.ActivityHistoryBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.history.HistoryView
import com.greenfarm.utils.getUserId

class HistoryActivity: BaseActivity<ActivityHistoryBinding>(ActivityHistoryBinding::inflate),HistoryView {


    override fun initAfterBinding() {
        var userId = getUserId()
        Log.d("userid",userId.toString())
        HistoryService.getHistories(this, userId.toString())

        binding.historyBackIc.setOnClickListener{
            finish()
        }
    }

    override fun onHistoryLoading() {

    }

    override fun onHistorySuccess(res: ArrayList<HistoryResult>) {
        drawHistoryBox(res)
    }

    override fun onHistoryFailure(code: Int, message: String) {
        when(code) {
            2001 -> {
                Log.d("message",message)
            }
            else -> {
                Log.d("message",message)
            }
        }
    }
    fun drawHistoryBox(res: ArrayList<HistoryResult>){
        for(i in res){
            Log.d("cropName","${i.cropName}")
            Log.d("sickNameKor","${i.sickNameKor}")
            Log.d("developmentCondition","${i.developmentCondition}")
            Log.d("preventionMethod","${i.preventionMethod}")
            Log.d("infectionRoute","${i.infectionRoute}")
            Log.d("symptoms","${i.symptoms}")
        }
    }


    fun makeHistoryBox(cropName: Int, sickNameKor:String){

    }
} 