package com.greenfarm.ui.login

//import com.greenfarm.data.remote.auth.Auth
import com.greenfarm.data.remote.auth.LoginResponse
import com.greenfarm.data.remote.auth.LoginResult

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(loginResult: LoginResult, id: String)
    fun onLoginFailure(code: Int, message: String)
}