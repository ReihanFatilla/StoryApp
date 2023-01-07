package com.reift.storyapp.data.remote.response.story

import com.google.gson.annotations.SerializedName

data class Story(
	val photoUrl: String,
	val createdAt: String,
	val name: String,
	val description: String,
	val id: String,
)
