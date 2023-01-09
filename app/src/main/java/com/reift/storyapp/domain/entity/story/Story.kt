package com.reift.storyapp.domain.entity.story

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
	val photoUrl: String,
	val createdAt: String,
	val name: String,
	val description: String,
	val id: String,
): Parcelable
