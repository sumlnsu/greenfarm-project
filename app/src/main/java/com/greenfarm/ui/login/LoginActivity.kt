package com.greenfarm.ui.login

import android.view.View
import android.widget.Toast
import com.greenfarm.data.entities.User
import com.greenfarm.data.remote.auth.Auth
import com.greenfarm.data.remote.auth.AuthService
import com.greenfarm.databinding.ActivityLoginBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.main.MainActivity
import com.greenfarm.ui.signup.SignUpActivity
import com.greenfarm.utils.saveJwt

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView, View.OnClickListener {

    override fun initAfterBinding() {
        binding.logInBtn.setOnClickListener(this)
        binding.signUpBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.signUpBtn -> startNextActivity(SignUpActivity::class.java)
            binding.logInBtn -> login()
        }
    }

    private fun login(){
        if(binding.logInId.text.toString().isEmpty()){
            binding.logInIdError.visibility = View.VISIBLE
            return
        }

        if(binding.logInPassword.text.toString().isEmpty()){
            binding.logInPasswordError.visibility = View.VISIBLE
            return
        }

        val id = binding.logInId.text.toString()
        val password = binding.logInPassword.text.toString()
        val user = User(id,password,"")
        AuthService.login(this,user)
    }

    override fun onLoginLoading() {
        binding.logInLoadingPb.visibility = View.VISIBLE

    }

    override fun onLoginSuccess(auth: Auth) {
        binding.logInLoadingPb.visibility = View.GONE

        saveJwt(auth.jwt)
        startActivityWithClear(MainActivity::class.java)

    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.logInLoadingPb.visibility = View.GONE

        when(code){
            2015,2019, 3014 ->{
                binding.logInIdError.visibility = View.VISIBLE
                binding.logInPasswordError.visibility = View.VISIBLE
            }
        }

    }
}