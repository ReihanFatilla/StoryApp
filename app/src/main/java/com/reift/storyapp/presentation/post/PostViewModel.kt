package com.reift.storyapp.presentation.post

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.post.Post
import com.reift.storyapp.domain.usecase.post.PostUseCase
import java.io.File

class PostViewModel(
    val postUseCase: PostUseCase
): ViewModel() {

    val postResponse = MediatorLiveData<Resource<Post>>()

    fun postStory(description: String, photo: File){
        val source = LiveDataReactiveStreams.fromPublisher(
            postUseCase.postStory(description, photo)
        )

        postResponse.addSource(source) {
            postResponse.postValue(it)
            postResponse.removeSource(source)
        }
    }

}