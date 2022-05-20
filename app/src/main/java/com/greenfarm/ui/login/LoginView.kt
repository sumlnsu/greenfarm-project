package com.greenfarm.ui.login

import com.greenfarm.data.remote.auth.LoginResult

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(loginResult: LoginResult)
    fun onLoginFailure(code: Int, message: String)
}