package com.reift.storyapp.data.remote.response.login

import com.google.gson.annotations.SerializedName

data class Login(
	val name: String? = null,
	val userId: String? = null,
	val token: String? = null
)
