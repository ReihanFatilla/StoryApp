package com.reift.storyapp.presentation.main

import androidx.lifecycle.ViewModel
import com.reift.storyapp.domain.usecase.story.StoryUseCase

class MainViewModel(
    val storyUseCase: StoryUseCase
): ViewModel() {
}