package com.greenfarm.ui.main

import android.content.Intent
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.greenfarm.R
import com.greenfarm.databinding.ActivityMainBinding
import com.greenfarm.databinding.ActivitySearchBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.TestActivity

class SearchActivity: BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {


    override fun initAfterBinding() {
        binding.categoryBackIc.setOnClickListener{
            finish()
        }

        binding.categoryBeanBt.setOnClickListener{
            binding.searchCategoryPartView.visibility = View.GONE
            binding.searchUploadPartView.visibility = View.VISIBLE
        }

        binding.categoryRedbeanBt.setOnClickListener{
            binding.searchCategoryPartView.visibility = View.GONE
            binding.searchUploadPartView.visibility = View.VISIBLE
        }

        binding.categorySesameBt.setOnClickListener{
            binding.searchCategoryPartView.visibility = View.GONE
            binding.searchUploadPartView.visibility = View.VISIBLE
        }


        binding.uploadBackIc.setOnClickListener{
            binding.searchUploadPartView.visibility = View.GONE
            binding.searchCategoryPartView.visibility = View.VISIBLE
        }
        binding.uploadStartTv.setOnClickListener{
            val  intent= Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }

}