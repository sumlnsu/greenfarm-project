package com.greenfarm.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.greenfarm.databinding.ActivitySplashBinding
import com.greenfarm.ui.BaseActivity
import com.greenfarm.ui.login.LoginActivity
import com.greenfarm.ui.main.MainActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun initAfterBinding() {
        Handler(Looper.getMainLooper()).postDelayed({
            val  intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

   /* private fun autoLogin() {
        AuthService.autoLogin(this)
    }

    override fun onAutoLoginLoading() {

    }

    override fun onAutoLoginSuccess() {
        startActivityWithClear(MainActivity::class.java)
    }

    override fun onAutoLoginFailure(code: Int, message: String) {
        startActivityWithClear(LoginActivity::class.java)
    }*/
}