package com.reift.storyapp.domain.usecase.story

import com.reift.storyapp.data.remote.response.story.Story
import com.reift.storyapp.domain.entity.Resource
import io.reactivex.rxjava3.core.Flowable

interface StoryUseCase {
    fun getAllStories(authToken: String): Flowable<Resource<List<Story>>>
}