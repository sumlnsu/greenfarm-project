package com.greenfarm.ui.login

import android.view.View
import com.greenfarm.data.remote.auth.Auth
import com.greenfarm.databinding.ActivityLoginBinding
import com.greenfarm.ui.BaseActivity

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