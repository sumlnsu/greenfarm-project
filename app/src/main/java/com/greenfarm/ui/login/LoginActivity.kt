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
        binding.loginBt.setOnClickListener(this)
        binding.loginSignUpBt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.loginSignUpBt -> startNextActivity(SignUpActivity::class.java)
            binding.loginBt -> startNextActivity(MainActivity::class.java)
        }
    }

    override fun onLoginLoading() {
        TODO("Not yet implemented")
    }

    override fun onLoginSuccess(auth: Auth) {
        TODO("Not yet implemented")
    }

    override fun onLoginFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    /*private fun login(){
        if(binding.loginId.text.toString().isEmpty()){
            binding.loginIdError.visibility = View.VISIBLE
            return
        }

        if(binding.loginPassword.text.toString().isEmpty()){
            binding.loginPasswordError.visibility = View.VISIBLE
            return
        }

        val id = binding.loginId.text.toString()
        val password = binding.loginPassword.text.toString()
        val user = User(id,password,"")
        AuthService.login(this,user)
    }

    override fun onLoginLoading() {
        binding.loginLoadingPb.visibility = View.VISIBLE

    }

    override fun onLoginSuccess(auth: Auth) {
        binding.loginLoadingPb.visibility = View.GONE

        saveJwt(auth.jwt)
        startActivityWithClear(MainActivity::class.java)

    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.loginLoadingPb.visibility = View.GONE

        when(code){
            2015,2019, 3014 ->{
                binding.loginIdError.visibility = View.VISIBLE
                binding.loginPasswordError.visibility = View.VISIBLE
            }
        }
    }*/
}