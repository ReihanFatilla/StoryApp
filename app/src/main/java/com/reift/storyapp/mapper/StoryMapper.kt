package com.reift.storyapp.mapper

import com.reift.storyapp.data.remote.response.story.Story
import com.reift.storyapp.data.remote.response.story.StoryResponse

object StoryMapper {
    fun StoryResponse.map(): List<Story> {
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
}