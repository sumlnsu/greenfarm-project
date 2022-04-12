package com.greenfarm.ui.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.greenfarm.R
import com.greenfarm.databinding.ActivityCategoryBinding
import com.greenfarm.databinding.ActivityMainBinding
import com.greenfarm.ui.BaseActivity

class CategoryActivity: BaseActivity<ActivityCategoryBinding>(ActivityCategoryBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment


    override fun initAfterBinding() {

    }
}