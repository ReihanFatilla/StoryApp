package com.reift.storyapp.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.domain.usecase.auth.AuthUseCase

class RegisterViewModel(
    private val authUseCase: AuthUseCase
): ViewModel() {

    val buttonEnabled = MutableLiveData(false)

    fun registerUser(name: String, email: String, password: String): LiveData<Register> {
        return authUseCase.registerUser(name, email, password)
    }

}
