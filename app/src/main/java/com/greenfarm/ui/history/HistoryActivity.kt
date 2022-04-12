package com.greenfarm.ui.main

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.greenfarm.R
import com.greenfarm.databinding.ActivityHistoryBinding
import com.greenfarm.databinding.ActivityMainBinding
import com.greenfarm.ui.BaseActivity

class HistoryActivity: BaseActivity<ActivityHistoryBinding>(ActivityHistoryBinding::inflate) {


    override fun initAfterBinding() {
        binding.historyBackIc.setOnClickListener{
            finish()
        }
    }
}