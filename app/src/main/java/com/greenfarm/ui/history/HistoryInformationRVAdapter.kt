package com.greenfarm.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import java.text.SimpleDateFormat;
import androidx.recyclerview.widget.RecyclerView
import com.greenfarm.data.entities.SearchSickNameResult
import com.greenfarm.data.remote.history.HistoryResult
import com.greenfarm.databinding.ItemHistoryBoxBinding
import com.greenfarm.databinding.ItemTestResultBinding
import com.greenfarm.ui.TestActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


class HistoryInformationRVAdapter(var informationlist : ArrayList<HistoryResult>, val context : Context) : RecyclerView.Adapter<HistoryInformationRVAdapter.ViewHolder>() {

    private var clickListener : BoxClickListener? = null

    interface BoxClickListener {
        fun boxClick(history : HistoryResult)
    }

    fun setBoxClickListener(boxClickListener: BoxClickListener){
        this.clickListener = boxClickListener
    }


    override fun getItemCount(): Int = informationlist.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: HistoryInformationRVAdapter.ViewHolder, position: Int) {
        holder.bind(informationlist[position], position)
        holder.setIsRecyclable(false)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HistoryInformationRVAdapter.ViewHolder {
        val binding: ItemHistoryBoxBinding = ItemHistoryBoxBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemHistoryBoxBinding): RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(historyResult: HistoryResult, position: Int){
            if(historyResult.cropName == "콩"){
                binding.historyBoxBeanIv.visibility=View.VISIBLE
                binding.historyBoxRedbeanIv.visibility=View.INVISIBLE
                binding.historyBoxSesameIv.visibility=View.INVISIBLE
            }
            else if(historyResult.cropName == "팥"){
                binding.historyBoxBeanIv.visibility=View.INVISIBLE
                binding.historyBoxRedbeanIv.visibility=View.VISIBLE
                binding.historyBoxSesameIv.visibility=View.INVISIBLE
            }
            else {
                binding.historyBoxBeanIv.visibility=View.INVISIBLE
                binding.historyBoxRedbeanIv.visibility=View.INVISIBLE
                binding.historyBoxSesameIv.visibility=View.VISIBLE
            }

            binding.historyBoxTimeTv.apply {
                // Time
                if(historyResult.currentTime.toLong() > 0){
                    var mil  = Date(historyResult.currentTime.toLong())
                    var dataFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))
                    text = dataFormat.format(mil).toString()
                }

            }

            binding.historyBoxSickTv.apply {
                text = historyResult.sickNameKor
            }

            binding.historyBoxLayout.setOnClickListener{
                // historyResult 전달
                clickListener?.boxClick(historyResult)
            }
        }
    }
}


