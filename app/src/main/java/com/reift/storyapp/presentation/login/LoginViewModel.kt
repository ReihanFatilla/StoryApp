package com.reift.storyapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.domain.usecase.auth.AuthUseCase

class LoginViewModel(
    val authUseCase: AuthUseCase
) : ViewModel() {

    val buttonEnabled = MutableLiveData(false)

    fun loginUser(email: String, password: String): LiveData<Login> {
        return authUseCase.loginUser(email, password)
    }

    fun isUserLogin(): Boolean {
        return authUseCase.isUserLogin()
    }

}