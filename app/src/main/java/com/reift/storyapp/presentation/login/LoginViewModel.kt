package com.reift.storyapp.presentation.login

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.usecase.auth.AuthUseCase
import io.reactivex.rxjava3.core.Flowable

class LoginViewModel(
    val authUseCase: AuthUseCase
) : ViewModel() {

    val loginResponse = MediatorLiveData<Resource<Login>>()

    val buttonEnabled = MutableLiveData(false)

    fun loginUser(email: String, password: String) {
        val source = LiveDataReactiveStreams.fromPublisher(
            authUseCase.loginUser(email, password)
        )

        loginResponse.addSource(source) {
            loginResponse.postValue(it)
            loginResponse.removeSource(source)
        }
    }

    fun isUserLogin(): Boolean {
        return authUseCase.isUserLogin()
    }

}