package com.greenfarm.ui.login

import com.greenfarm.data.remote.auth.Auth

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(auth: Auth, id : String)
    fun onLoginFailure(code: Int, message: String)
}