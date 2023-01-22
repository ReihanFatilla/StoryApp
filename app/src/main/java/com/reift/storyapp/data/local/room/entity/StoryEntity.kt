package com.reift.storyapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "story")
data class StoryEntity(
    @PrimaryKey
    val id: String? = null,
    val photoUrl: String? = null,
    val createdAt: String? = null,
    val name: String? = null,
    val description: String? = null,
    val lon: Double? = null,
    val lat: Double? = null
)
