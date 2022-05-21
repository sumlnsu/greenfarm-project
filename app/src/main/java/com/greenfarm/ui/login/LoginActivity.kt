package com.greenfarm.ui.login

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.greenfarm.ApplicationClass
import com.greenfarm.data.entities.User
//import com.greenfarm.data.remote.auth.Auth
import com.greenfarm.data.remote.auth.AuthService
import com.greenfarm.data.remote.auth.LoginResponse
import com.greenfarm.data.remote.auth.LoginResult
import com.greenfarm.databinding.ActivityLoginBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.MyFirebaseMessagingService
import com.greenfarm.ui.TestActivity
import com.greenfarm.ui.main.MainActivity
import com.greenfarm.ui.signup.SignUpActivity
import com.greenfarm.utils.getJwt
import com.greenfarm.utils.saveJwt
import com.greenfarm.utils.saveUserId

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView, View.OnClickListener {

    override fun initAfterBinding() {


        binding.loginBt.setOnClickListener(this)
        binding.loginSignUpBt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.loginSignUpBt -> startNextActivity(SignUpActivity::class.java)
            binding.loginBt -> {
                login()

            }
            // 로그인이 되었을 때 유저 아이디와 토큰을 데이터베이스에 저장 -> 메인 엑티비티에서 실행해도 됨
        }
    }

    private fun login(){
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

    override fun onLoginSuccess(loginResult: LoginResult, id: String) {
        binding.loginLoadingPb.visibility = View.GONE

        saveJwt(loginResult.jwt)
        saveUserId(loginResult.userid)
        val intent= Intent(this, MainActivity::class.java)

        finish()
        startActivity(intent)

    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.loginLoadingPb.visibility = View.GONE

        when(code){
            2015,2019, 3014 ->{
                binding.loginIdError.visibility = View.VISIBLE
                binding.loginPasswordError.visibility = View.VISIBLE
            }
        }
    }
}