package com.greenfarm.ui.main

import android.content.Intent
import com.greenfarm.databinding.ActivityMainBinding
import com.greenfarm.ui.BaseActivity

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {


    override fun initAfterBinding() {
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