package com.reift.storyapp.data.remote.response.story

import com.google.gson.annotations.SerializedName

data class Story(
	val photoUrl: String? = null,
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val id: String? = null,
)
