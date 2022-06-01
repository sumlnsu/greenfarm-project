package com.greenfarm.ui.history

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greenfarm.R
import com.greenfarm.data.entities.SearchSickNameResult
import com.greenfarm.data.remote.history.HistoryResult
import com.greenfarm.data.remote.history.HistoryService
import com.greenfarm.databinding.ActivityHistoryBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.SickInformationRVAdapter
import com.greenfarm.ui.TestActivity
import com.greenfarm.ui.history.HistoryView
import com.greenfarm.utils.getUserId

class HistoryActivity: BaseActivity<ActivityHistoryBinding>(ActivityHistoryBinding::inflate),HistoryView,HistoryInformationRVAdapter.BoxClickListener {

    var historyInformation = ArrayList<HistoryResult>()
    val historyInformationRVAdapter = HistoryInformationRVAdapter(historyInformation, this)

    override fun initAfterBinding() {
        var userId = getUserId()
        Log.d("userid",userId.toString())


        HistoryService.getHistories(this, userId.toString())


        binding.historyBackIc.setOnClickListener{
            finish()
        }

        var recyclerView = findViewById<RecyclerView>(R.id.history_item_recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = historyInformationRVAdapter
        historyInformationRVAdapter.setBoxClickListener(this)
    }

    override fun onHistoryLoading() {}

    override fun onHistorySuccess(res: ArrayList<HistoryResult>) {
        for(i in res){
            historyInformation.add(i)
        }
        historyInformationRVAdapter.notifyDataSetChanged()
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


    override fun boxClick(history : HistoryResult) {
        val intent = Intent(this, TestActivity::class.java)
        intent.putExtra("IsLog", true)
        intent.putExtra("image", history.imgPath)
        intent.putExtra("sickName",history.sickNameKor)
        intent.putExtra("infectionRoute",history.infectionRoute)
        intent.putExtra("preventionMethod",history.preventionMethod)
        intent.putExtra("developmentCondition",history.developmentCondition)
        intent.putExtra("symptoms",history.symptoms)
        startActivity(intent)
    }
} 