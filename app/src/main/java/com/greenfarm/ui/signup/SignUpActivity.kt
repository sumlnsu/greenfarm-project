package com.greenfarm.ui.signup

import android.view.View
import android.widget.Toast
import com.greenfarm.data.entities.User
import com.greenfarm.data.remote.auth.AuthService
import com.greenfarm.databinding.ActivitySignupBinding
import com.greenfarm.ui.BaseActivity

class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate), SignUpView, View.OnClickListener {

    override fun initAfterBinding() {

        binding.signUpBack.setOnClickListener{
            finish()
        }
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
        }
    }

    override fun onSignUpLoading() {
        TODO("Not yet implemented")
    }

    override fun onSignUpSuccess() {
        TODO("Not yet implemented")
    }

    override fun onSignUpFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

}