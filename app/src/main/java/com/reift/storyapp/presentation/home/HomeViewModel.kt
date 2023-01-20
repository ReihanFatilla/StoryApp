package com.reift.storyapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.reift.storyapp.domain.entity.story.Story
import com.reift.storyapp.domain.usecase.story.StoryUseCase
import io.reactivex.rxjava3.core.Flowable

class HomeViewModel(
    val storyUseCase: StoryUseCase
): ViewModel() {

    fun getAllStories(): Flowable<PagingData<Story>> {
        return storyUseCase.getAllStories().cachedIn(viewModelScope)
    }

    fun logout(){
        storyUseCase.logout()
    }

}