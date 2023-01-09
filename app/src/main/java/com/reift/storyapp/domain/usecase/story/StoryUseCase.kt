package com.reift.storyapp.domain.usecase.story

import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.story.Story
import io.reactivex.rxjava3.core.Flowable

interface StoryUseCase {
    fun getAllStories(): Flowable<Resource<List<Story>>>
    fun logout()
}