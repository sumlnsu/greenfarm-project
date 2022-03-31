package com.greenfarm.ui.splash

import com.greenfarm.data.remote.auth.Auth

interface SplashView {
    fun onAutoLoginLoading()
    fun onAutoLoginSuccess()
    fun onAutoLoginFailure(code: Int, message: String)
}