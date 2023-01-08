package com.reift.storyapp.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reift.storyapp.domain.usecase.auth.AuthUseCase

class LoginViewModel(
    val authUseCase: AuthUseCase
): ViewModel() {
    val buttonEnabled = MutableLiveData(false)
}