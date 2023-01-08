package com.reift.storyapp.presentation.register

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.usecase.auth.AuthUseCase

class RegisterViewModel(
    val authUseCase: AuthUseCase
): ViewModel() {

    val registerResponse = MediatorLiveData<Resource<Register>>()

    val buttonEnabled = MutableLiveData(false)

    fun registerUser(name: String, email: String, password: String) {
        val source = LiveDataReactiveStreams.fromPublisher(
            authUseCase.registerUser(name, email, password)
        )

        registerResponse.addSource(source) {
            registerResponse.postValue(it)
            registerResponse.removeSource(source)
        }
    }

}
