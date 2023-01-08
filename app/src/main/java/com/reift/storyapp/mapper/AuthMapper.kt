package com.reift.storyapp.mapper

import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.remote.response.login.LoginResponse
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.data.remote.response.register.RegisterResponse

object AuthMapper {
    fun LoginResponse.map(): Login {
        return with(loginResult) {
            Login(
                isError = error ?: true,
                name = name.orEmpty(),
                userId = userId.orEmpty(),
                token = token.orEmpty()
            )
        }
    }

    fun RegisterResponse.map(): Register {
        return Register(error ?: true, message.orEmpty())
    }
}