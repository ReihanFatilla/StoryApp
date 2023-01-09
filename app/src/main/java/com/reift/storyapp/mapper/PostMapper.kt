package com.reift.storyapp.mapper

import com.reift.storyapp.data.remote.response.post.PostResponse
import com.reift.storyapp.domain.entity.post.Post

object PostMapper {
    fun PostResponse.map(): Post {
        return  Post(
            error = error ?: true,
            message = message.orEmpty()
        )
    }
}