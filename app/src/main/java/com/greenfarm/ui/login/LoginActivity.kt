package com.greenfarm.ui.login

import android.view.View
import android.widget.Toast
import com.greenfarm.data.entities.User
import com.greenfarm.data.remote.auth.Auth
import com.greenfarm.data.remote.auth.AuthService
import com.greenfarm.databinding.ActivityLoginBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.main.CategoryActivity
import com.greenfarm.ui.signup.SignUpActivity
import com.greenfarm.utils.saveJwt

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView, View.OnClickListener {

    override fun initAfterBinding() {
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
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
}