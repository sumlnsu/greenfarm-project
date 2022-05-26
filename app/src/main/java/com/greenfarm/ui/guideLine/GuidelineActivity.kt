package com.greenfarm.ui.guideLine

import android.content.Intent
import com.greenfarm.databinding.ActivityGuideBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.main.SearchActivity

class GuidelineActivity : BaseActivity<ActivityGuideBinding>(ActivityGuideBinding::inflate){
    override fun initAfterBinding() {
        binding.guideBackBt.setOnClickListener{
            val intent = Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }
    }

}