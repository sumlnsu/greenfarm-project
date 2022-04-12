package com.greenfarm.ui.main

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.greenfarm.R
import com.greenfarm.databinding.ActivityMainBinding
import com.greenfarm.ui.BaseActivity

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment


    override fun initAfterBinding() {
        binding.mainSearchBtIv.setOnClickListener{
            val  intent= Intent(this, CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}