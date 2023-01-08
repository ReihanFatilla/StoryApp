package com.reift.storyapp.data.remote.response.register

import com.google.gson.annotations.SerializedName

data class Register(
	val isError: Boolean,
	val message: String
)
