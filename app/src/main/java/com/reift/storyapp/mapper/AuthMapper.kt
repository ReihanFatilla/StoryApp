package com.reift.storyapp.mapper

import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.remote.response.login.LoginResponse

object AuthMapper {
    fun LoginResponse.map(): Login {
        return with(loginResult) {
            Login(
                name = name.orEmpty(),
                userId = userId.orEmpty(),
                token = token.orEmpty()
            )
        }
    }
}