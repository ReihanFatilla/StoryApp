package com.reift.storyapp.data.remote.response.login

data class Login(
	val isError: Boolean,
	val message: String,
	val name: String,
	val userId: String,
	val token: String
)
