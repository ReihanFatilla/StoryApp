package com.reift.storyapp.presentation.login

import androidx.lifecycle.*
import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.usecase.auth.AuthUseCase
import io.reactivex.rxjava3.core.Flowable

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