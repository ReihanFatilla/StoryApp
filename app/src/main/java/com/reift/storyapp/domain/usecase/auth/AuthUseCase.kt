package com.reift.storyapp.domain.usecase.auth

import androidx.lifecycle.LiveData
import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.domain.entity.Resource
import io.reactivex.rxjava3.core.Flowable

interface AuthUseCase {
    fun registerUser(name: String, email: String, password: String): LiveData<Register>
    fun loginUser(email: String, password: String): LiveData<Login>
    fun isUserLogin(): Boolean
}