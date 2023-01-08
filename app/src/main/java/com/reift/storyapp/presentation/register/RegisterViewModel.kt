package com.reift.storyapp.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reift.storyapp.domain.usecase.auth.AuthUseCase

class RegisterViewModel(
    val authUseCase: AuthUseCase
): ViewModel() {

    val buttonEnabled = MutableLiveData(false)

}
