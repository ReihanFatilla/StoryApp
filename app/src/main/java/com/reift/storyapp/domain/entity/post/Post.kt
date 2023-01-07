package com.reift.storyapp.data.remote.response.post

import com.google.gson.annotations.SerializedName

data class Post(
	val error: Boolean,
	val message: String
)
