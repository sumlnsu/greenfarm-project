package com.eight.collection.ui.splash

import com.eight.collection.data.remote.auth.Auth

interface SplashView {
    fun onAutoLoginLoading()
    fun onAutoLoginSuccess()
    fun onAutoLoginFailure(code: Int, message: String)
}