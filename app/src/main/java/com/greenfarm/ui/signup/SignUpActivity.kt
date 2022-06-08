package com.greenfarm.ui.signup

import android.util.Log
import android.view.View
import android.widget.Toast
import com.greenfarm.data.entities.User
import com.greenfarm.data.remote.auth.AuthService
import com.greenfarm.databinding.ActivitySignupBinding
import com.greenfarm.ui.BaseActivity

class SignUpActivity: BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate), SignUpView, View.OnClickListener {

    override fun initAfterBinding() {
        // 왼쪽 상단 Back ICON Click Event (Finish Activity)
        binding.signUpBack.setOnClickListener{
            finish()
        }
        binding.signUpBackTv.setOnClickListener{
            finish()
        }
        // 회원가입 버튼 클릭시 Event (지금은 Finish지만 신현이한테 명세서 받고, 회원가입 기능 추가)
        binding.signUpBtn.setOnClickListener{
            val pw = binding.signUpPasswordEt.text.toString()
            val pwc = binding.signUpPasswordConfirmEt.text.toString()

            if(pw == pwc){
                signUp()
            }
            else{
                binding.signUpPasswordError.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(v: View?) {

    }


    private fun getUser() : User {
        val id : String = binding.signUpIdEt.text.toString()
        val pw : String = binding.signUpPasswordEt.text.toString()
        val location : String = binding.signUpFarmEt.text.toString()

        return User(id,pw,location)
    }

    private fun signUp(){
        AuthService.signUp(this, getUser())
    }


    override fun onSignUpLoading() {}

    override fun onSignUpSuccess() {
        Toast.makeText(this@SignUpActivity, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSignUpFailure(code: Int, message: String) {
        when(code) {
            4000, 4011 -> {
                Log.d("Error-message",message)
            }
        }
    }

}