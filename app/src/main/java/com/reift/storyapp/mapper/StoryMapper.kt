package com.reift.storyapp.mapper

import com.reift.storyapp.data.local.room.entity.StoryEntity
import com.reift.storyapp.data.remote.response.story.StoryResponse
import com.reift.storyapp.domain.entity.location.Location
import com.reift.storyapp.domain.entity.story.Story

object StoryMapper {

    fun StoryEntity.mapStory(): Story {
        return Story(
            photoUrl = photoUrl.orEmpty(),
            createdAt = createdAt.orEmpty(),
            name = name.orEmpty(),
            description = description.orEmpty(),
            id = id.orEmpty(),
            lat = lat ?: 0.0,
            lon = lon ?: 0.0,
        )
    }

    fun StoryResponse.mapStory(): List<Story> {
        return listStory?.map { story ->
            with(story) {
                Story(
                    photoUrl = photoUrl.orEmpty(),
                    createdAt = createdAt.orEmpty(),
                    name = name.orEmpty(),
                    description = description.orEmpty(),
                    id = id.orEmpty(),
                    lat = lat ?: 0.0,
                    lon = lon ?: 0.0,
                )
            }
        } ?: listOf()
    }

    fun StoryResponse.mapEntity(): List<StoryEntity> {
        return listStory?.map { story ->
            with(story) {
                StoryEntity(
                    photoUrl = photoUrl.orEmpty(),
                    createdAt = createdAt.orEmpty(),
                    name = name.orEmpty(),
                    description = description.orEmpty(),
                    id = id.orEmpty(),
                    lat = lat ?: 0.0,
                    lon = lon ?: 0.0
                )
            }
        } ?: listOf()
    }

    fun StoryResponse.mapLocation(): List<Location> {
        return listStory?.map { story ->
            with(story) {
                Location(
                    long = lon ?: 0.0,
                    lat = lat ?: 0.0,
                    creator = name.orEmpty(),
                )
            }
        } ?: listOf()
    }
}