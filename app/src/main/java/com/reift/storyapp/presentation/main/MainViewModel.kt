package com.reift.storyapp.presentation.main

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.reift.storyapp.data.remote.response.story.Story
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.usecase.story.StoryUseCase

class MainViewModel(
    val storyUseCase: StoryUseCase
): ViewModel() {

    val storyResponse = MediatorLiveData<Resource<List<Story>>>()

    fun getAllStories(){
        val source = LiveDataReactiveStreams.fromPublisher(
            storyUseCase.getAllStories()
        )

        storyResponse.addSource(source) {
            storyResponse.postValue(it)
            storyResponse.removeSource(source)
        }
    }

}