package com.reift.storyapp.data.remote.response.post

import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
