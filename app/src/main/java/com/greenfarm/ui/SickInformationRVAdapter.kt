package com.greenfarm.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greenfarm.data.entities.SearchSickNameResult
import com.greenfarm.databinding.ItemTestResultBinding
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


class SickInformationRVAdapter(var informationlist : ArrayList<SearchSickNameResult>, val context : Context) : RecyclerView.Adapter<SickInformationRVAdapter.ViewHolder>() {

    override fun getItemCount(): Int = informationlist.size

    override fun onBindViewHolder(holder: SickInformationRVAdapter.ViewHolder, position: Int) {
        holder.bind(informationlist[position], position)
        holder.setIsRecyclable(false)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SickInformationRVAdapter.ViewHolder {
        val binding: ItemTestResultBinding = ItemTestResultBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemTestResultBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(searchSicknameResult: SearchSickNameResult, position: Int){
            binding.testResultSicknameReceive.apply {
                if(searchSicknameResult.sickNameKor == null){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else if(searchSicknameResult.sickNameKor == "null"){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else {
                    text = searchSicknameResult.sickNameKor
                }
                if (searchSicknameResult.sickNameKor == "세균성점무늬병"){
                    setTextColor(Color.RED)
                }
                else if (searchSicknameResult.sickNameKor == "흰가루병"){
                    setTextColor(Color.GREEN)
                }
                else if (searchSicknameResult.sickNameKor == "리조푸스"){
                    setTextColor(Color.RED)
                }
                else if (searchSicknameResult.sickNameKor == "잎마름병"){
                    setTextColor(Color.BLUE)
                }
                else if (searchSicknameResult.sickNameKor == "Powdery mildew1"){
                    setTextColor(Color.GREEN)
                }
                else {
                    setTextColor(Color.CYAN)
                }
            }

            binding.testResultDevelopmentConditionRecieve.apply {
                if(searchSicknameResult.developmentCondition == null){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else if(searchSicknameResult.developmentCondition == "null"){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else{
                    text = searchSicknameResult.developmentCondition
                }
            }

            binding.testResultSymptomsRecieve.apply {
                if(searchSicknameResult.symptoms == null){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else if(searchSicknameResult.symptoms == "null"){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else {
                    text = searchSicknameResult.symptoms
                }
            }

            binding.testResultPreventionMethodReceive.apply {
                if(searchSicknameResult.preventionMethod == null){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else if(searchSicknameResult.preventionMethod == "null"){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else {
                    text = searchSicknameResult.preventionMethod
                }
            }

            binding.testResultInfectionRouteReceive.apply {
                if(searchSicknameResult.infectionRoute == null){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else if(searchSicknameResult.infectionRoute == "null"){
                    text = "해당 정보가 존재하지 않습니다."
                }
                else {
                    text = searchSicknameResult.infectionRoute
                }
            }
        }
    }
}


