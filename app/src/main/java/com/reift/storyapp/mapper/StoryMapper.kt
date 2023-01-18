package com.reift.storyapp.mapper

import com.reift.storyapp.data.remote.response.story.StoryResponse
import com.reift.storyapp.domain.entity.location.Location
import com.reift.storyapp.domain.entity.story.Story

object StoryMapper {
    fun StoryResponse.mapStory(): List<Story> {
        return listStory?.map { story ->
            with(story) {
                Story(
                    photoUrl = photoUrl.orEmpty(),
                    createdAt = createdAt.orEmpty(),
                    name = name.orEmpty(),
                    description = description.orEmpty(),
                    id = id.orEmpty()
                )
            }
        } ?: listOf()
    }

    fun StoryResponse.mapLocation(): List<Location> {
        return listStory?.map { story ->
            with(story) {
                Location(
                    long = lon ?: 0.0,
                    lat = lat ?: 0.0
                )
            }
        } ?: listOf()
    }
}