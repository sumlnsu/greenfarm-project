package com.greenfarm.ui.main

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.greenfarm.R
import com.greenfarm.databinding.ActivityMainBinding
import com.greenfarm.databinding.ActivitySearchBinding
import com.greenfarm.ui.BaseActivity

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
    }

}