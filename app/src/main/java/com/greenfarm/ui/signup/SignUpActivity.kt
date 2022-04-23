package com.greenfarm.ui.signup

import android.view.View
import android.widget.Toast
import com.greenfarm.data.entities.User
import com.greenfarm.data.remote.auth.AuthService
import com.greenfarm.databinding.ActivitySignupBinding
import com.greenfarm.ui.BaseActivity

class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate), SignUpView, View.OnClickListener {

    override fun initAfterBinding() {

        // 왼쪽 상단 Back Zone Click Event (Finish Activity)
        binding.signUpBack.setOnClickListener{
            finish()
        }
        binding.signUpBackTv.setOnClickListener{
            finish()
        }

        // 회원가입 버튼 클릭시 Event (지금은 Finish지만 신현이한테 명세서 받고, 회원가입 기능 추가)
        binding.signUpBtn.setOnClickListener{
            finish()
        }
    }

    override fun onClick(v: View?) {

    }


    override fun onSignUpLoading() {

    }

    override fun onSignUpSuccess() {

    }

    override fun onSignUpFailure(code: Int, message: String) {

    }

}