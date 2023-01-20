package com.reift.storyapp.domain.usecase.story

import androidx.paging.PagingData
import com.reift.storyapp.domain.entity.story.Story
import io.reactivex.rxjava3.core.Flowable

interface StoryUseCase {
    fun getAllStories(): Flowable<PagingData<Story>>
    fun logout()
}