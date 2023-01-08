package com.reift.storyapp.domain.usecase.auth

import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.domain.entity.Resource
import io.reactivex.rxjava3.core.Flowable

interface AuthUseCase {
    fun registerUser(name: String, email: String, password: String): Flowable<Resource<Register>>
    fun loginUser(email: String, password: String): Flowable<Resource<Login>>
    fun isUserLogin(): Boolean
}