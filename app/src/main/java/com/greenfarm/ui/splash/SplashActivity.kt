package com.greenfarm.ui.splash

import android.os.Handler
import android.os.Looper
import com.greenfarm.data.remote.auth.AuthService
import com.greenfarm.databinding.ActivitySplashBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.login.LoginActivity
import com.greenfarm.ui.main.MainActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), SplashView {

    override fun initAfterBinding() {
        Handler(Looper.getMainLooper()).postDelayed({
            autoLogin()
        }, 2000)
    }

    private fun autoLogin() {
        AuthService.autoLogin(this)
    }

    override fun onAutoLoginLoading() {

    }

    override fun onAutoLoginSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
        startActivityWithClear(LoginActivity::class.java)
    }
}